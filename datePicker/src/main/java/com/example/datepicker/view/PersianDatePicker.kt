package com.example.datepicker.view

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import com.example.datepicker.R
import com.example.datepicker.api.PersianPickerDate
import com.example.datepicker.date.PersianDateImpl
import com.example.datepicker.utils.PersianCalendar
import com.example.datepicker.utils.PersianHelper
import java.util.*

internal class PersianDatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) :
    LinearLayout(context, attrs, defStyle) {
    private val persianDate: PersianPickerDate
    private var selectedMonth = 0
    private var selectedYear = 0
    private var selectedDay = 0
    private var displayMonthNames = false
    private var mListener: OnDateChangedListener? = null
    private val yearNumberPicker: @Composable ()->Unit
    private val monthNumberPicker:  @Composable ()->Unit
    private val dayNumberPicker:  @Composable ()->Unit
    private var minYear = 0
    private var maxYear = 0
    private var maxMonth = 0
    private var maxDay = 0
    private var displayDescription = false
    private val descriptionTextView: TextView
    private var typeFace: Typeface? = null
    private var dividerColor = 0
    private var yearRange = 0
    private fun updateVariablesFromXml(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PersianDatePicker, 0, 0)
        yearRange = a.getInteger(R.styleable.PersianDatePicker_yearRange, 10)
        /*
         * Initializing yearNumberPicker min and max values If minYear and
         * maxYear attributes are not set, use (current year - 10) as min and
         * (current year + 10) as max.
         */minYear = a.getInt(
            R.styleable.PersianDatePicker_minYear,
            persianDate.getPersianYear() - yearRange
        )
        maxYear = a.getInt(
            R.styleable.PersianDatePicker_maxYear,
            persianDate.getPersianYear() + yearRange
        )
        displayMonthNames = a.getBoolean(R.styleable.PersianDatePicker_displayMonthNames, false)
        /*
         * displayDescription
         */displayDescription =
            a.getBoolean(R.styleable.PersianDatePicker_displayDescription, false)
        selectedDay =
            a.getInteger(R.styleable.PersianDatePicker_selectedDay, persianDate.getPersianDay())
        selectedYear =
            a.getInt(R.styleable.PersianDatePicker_selectedYear, persianDate.getPersianYear())
        selectedMonth =
            a.getInteger(R.styleable.PersianDatePicker_selectedMonth, persianDate.getPersianMonth())

        // if you pass selected year before min year, then we need to push min year to before that
        if (minYear > selectedYear) {
            minYear = selectedYear - yearRange
        }
        if (maxYear < selectedYear) {
            maxYear = selectedYear + yearRange
        }
        a.recycle()
    }

    fun setMaxYear(maxYear: Int) {
        this.maxYear = maxYear
        updateViewData()
    }

    fun setMaxMonth(maxMonth: Int) {
        this.maxMonth = maxMonth
        updateViewData()
    }

    fun setMaxDay(maxDay: Int) {
        this.maxDay = maxDay
        updateViewData()
    }

    fun setMinYear(minYear: Int) {
        this.minYear = minYear
        updateViewData()
    }
    fun setTypeFace(typeFace: Typeface?) {
        this.typeFace = typeFace
        updateViewData()
    }

    fun setDividerColor(@ColorInt color: Int) {
        dividerColor = color
        updateViewData()
    }

    private fun setDividerColor(picker: NumberPicker, color: Int) {
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(color)
                    pf[picker] = colorDrawable
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: NotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
                break
            }
        }
    }

    private fun updateViewData() {
        if (typeFace != null) {
            yearNumberPicker.setTypeFace(typeFace)
            monthNumberPicker.setTypeFace(typeFace)
            dayNumberPicker.setTypeFace(typeFace)
        }
        if (dividerColor > 0) {
            setDividerColor(yearNumberPicker, dividerColor)
            setDividerColor(monthNumberPicker, dividerColor)
            setDividerColor(dayNumberPicker, dividerColor)
        }
        yearNumberPicker.setMinValue(minYear)
        yearNumberPicker.setMaxValue(maxYear)
        if (selectedYear > maxYear) {
            selectedYear = maxYear
        }
        if (selectedYear < minYear) {
            selectedYear = minYear
        }
        yearNumberPicker.setValue(selectedYear)
        yearNumberPicker.setOnValueChangedListener(dateChangeListener)

        /*
         * initialing monthNumberPicker
         */monthNumberPicker.setMinValue(1)
        monthNumberPicker.setMaxValue(if (maxMonth > 0) maxMonth else 12)
        if (displayMonthNames) {
            monthNumberPicker.setDisplayedValues(PersianCalendarConstants.persianMonthNames)
        }
        require(!(selectedMonth < 1 || selectedMonth > 12)) {
            String.format(
                "Selected month (%d) must be between 1 and 12",
                selectedMonth
            )
        }
        monthNumberPicker.setValue(selectedMonth)
        monthNumberPicker.setOnValueChangedListener(dateChangeListener)

        /*
         * initializing dayNumberPicker
         */dayNumberPicker.setMinValue(1)
        setDayNumberPickerMaxValue(31)
        require(!(selectedDay > 31 || selectedDay < 1)) {
            String.format(
                "Selected day (%d) must be between 1 and 31",
                selectedDay
            )
        }
        if (selectedMonth > 6 && selectedMonth < 12 && selectedDay == 31) {
            selectedDay = 30
        } else {
            val isLeapYear: Boolean = PersianDateImpl.isLeapYear(selectedYear)
            if (isLeapYear && selectedDay == 31) {
                selectedDay = 30
            } else if (selectedDay > 29) {
                selectedDay = 29
            }
        }
        dayNumberPicker.setValue(selectedDay)
        dayNumberPicker.setOnValueChangedListener(dateChangeListener)
        if (displayDescription) {
            descriptionTextView.visibility = VISIBLE
            descriptionTextView.setText(persianDate.getPersianLongDate())
        }
    }

    var dateChangeListener =
        OnValueChangeListener { picker, oldVal, newVal ->
            val year: Int = yearNumberPicker.getValue()
            val isLeapYear: Boolean = PersianDateImpl.isLeapYear(year)
            val month: Int = monthNumberPicker.getValue()
            val day: Int = dayNumberPicker.getValue()
            if (month < 7) {
                dayNumberPicker.setMinValue(1)
                setDayNumberPickerMaxValue(31)
            } else if (month < 12) {
                if (day == 31) {
                    dayNumberPicker.setValue(30)
                }
                dayNumberPicker.setMinValue(1)
                setDayNumberPickerMaxValue(30)
            } else if (month == 12) {
                if (isLeapYear) {
                    if (day == 31) {
                        dayNumberPicker.setValue(30)
                    }
                    dayNumberPicker.setMinValue(1)
                    setDayNumberPickerMaxValue(30)
                } else {
                    if (day > 29) {
                        dayNumberPicker.setValue(29)
                    }
                    dayNumberPicker.setMinValue(1)
                    setDayNumberPickerMaxValue(29)
                }
            }
            persianDate.setDate(
                year,
                month,
                day
            )

            // Set description
            if (displayDescription) {
                descriptionTextView.setText(persianDate.getPersianLongDate())
            }
            if (mListener != null) {
                mListener!!.onDateChanged(year, month, day)
            }
        }

    fun setDayNumberPickerMaxValue(value: Int) {
        if (monthNumberPicker.getValue() === maxMonth) {
            if (maxDay > 0) {
                dayNumberPicker.setMaxValue(maxDay)
            } else {
                dayNumberPicker.setMaxValue(value)
            }
        } else {
            dayNumberPicker.setMaxValue(value)
        }
    }

    fun setOnDateChangedListener(onDateChangedListener: OnDateChangedListener?) {
        mListener = onDateChangedListener
    }

    /**
     * The callback used to indicate the user changed the date.
     * A class that wants to be notified when the date of PersianDatePicker
     * changes should implement this interface and register itself as the
     * listener of date change events using the PersianDataPicker's
     * setOnDateChangedListener method.
     */
    interface OnDateChangedListener {
        /**
         * Called upon a date change.
         *
         * @param newYear  The year that was set.
         * @param newMonth The month that was set (1-12)
         * @param newDay   The day of the month that was set.
         */
        fun onDateChanged(newYear: Int, newMonth: Int, newDay: Int)
    }

    var displayDate: Date?
        get() = persianDate.getGregorianDate()
        set(displayDate) {
            persianDate.setDate(displayDate)
            displayPersianDate = persianDate
        }
    /**
     * @return [PersianCalendar] that indicate current calendar state
     * @Deprecated Use getPersianDate() instead
     */
    /**
     * @Deprecated Use setDisplayPersianDate(PersianPickerDate displayPersianDate) instead
     */
    @get:Deprecated("")
    @set:Deprecated("")
    var displayPersianDate: PersianCalendar
        get() {
            val persianCalendar = PersianCalendar()
            persianCalendar.setPersianDate(
                persianDate.getPersianYear(),
                persianDate.getPersianMonth(),
                persianDate.getPersianDay()
            )
            return persianCalendar
        }
        set(displayPersianDate) {
            val persianPickerDate: PersianPickerDate = PersianDateImpl()
            persianPickerDate.setDate(
                displayPersianDate.getPersianYear(),
                displayPersianDate.getPersianMonth(),
                displayPersianDate.getPersianDay()
            )
            setDisplayPersianDate(persianPickerDate)
        }

    fun getPersianDate(): PersianPickerDate {
        return persianDate
    }

    fun setDisplayPersianDate(displayPersianDate: PersianPickerDate) {
        persianDate.setDate(displayPersianDate.timestamp)
        val year: Int = persianDate.persianYear
        val month: Int = persianDate.persianMonth
        val day: Int = persianDate.persianDay
        selectedYear = year
        selectedMonth = month
        selectedDay = day

        // if you pass selected year before min year, then we need to push min year to before that
        if (minYear > selectedYear) {
            minYear = selectedYear - yearRange
            yearNumberPicker.setMinValue(minYear)
        }

        // if you pass selected year after max year, then we need to push max year to after that
        if (maxYear < selectedYear) {
            maxYear = selectedYear + yearRange
            yearNumberPicker.setMaxValue(maxYear)
        }
        yearNumberPicker.post(Runnable { yearNumberPicker.setValue(year) })
        monthNumberPicker.post(Runnable { monthNumberPicker.setValue(month) })
        dayNumberPicker.post(Runnable { dayNumberPicker.setValue(day) })
    }

    override fun onSaveInstanceState(): Parcelable? {
        // begin boilerplate code that allows parent classes to save state
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        // end
        ss.datetime = displayDate!!.time
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        // begin boilerplate code so parent classes can restore state
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val ss = state
        super.onRestoreInstanceState(ss.superState)
        // end
        displayDate = Date(ss.datetime)
    }

    internal class SavedState : BaseSavedState {
        var datetime: Long = 0

        constructor(superState: Parcelable?) : super(superState) {}
        private constructor(`in`: Parcel) : super(`in`) {
            datetime = `in`.readLong()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeLong(datetime)
        }

        companion object {
            // required field that makes Parcelables from a Parcel
            val CREATOR: Creator<SavedState> = object : Creator<SavedState?> {
                override fun createFromParcel(`in`: Parcel): SavedState? {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    init {

        // inflate views
        val view: View = LayoutInflater.from(context).inflate(R.layout.sl_persian_date_picker, this)

        // get views
        yearNumberPicker = view.findViewById(R.id.yearNumberPicker)
        monthNumberPicker = view.findViewById(R.id.monthNumberPicker)
        dayNumberPicker = view.findViewById(R.id.dayNumberPicker)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        yearNumberPicker.setFormatter(NumberPicker.Formatter { i -> PersianHelper.toPersianNumber(i.toString() + "") })
        monthNumberPicker.setFormatter(NumberPicker.Formatter { i -> PersianHelper.toPersianNumber(i.toString() + "") })
        dayNumberPicker.setFormatter(NumberPicker.Formatter { i -> PersianHelper.toPersianNumber(i.toString() + "") })

        // init calendar
        persianDate = PersianDateImpl()

        // update variables from xml
        updateVariablesFromXml(context, attrs)

        // update view
        updateViewData()
    }
}