package com.example.datepicker.utils

import com.example.datepicker.date.PersianDateImpl
import java.util.*

class PersianCalendar : GregorianCalendar {
    // calculatePersianDate();
    var persianYear = 0
        private set
    private var persianMonth = 0// calculatePersianDate();

    /**
     *
     * @return int Persian day in month
     */
    var persianDay = 0
        private set

    /**
     * assign delimiter to use as a separator of date fields.
     *
     * @param delimiter
     */
    // use to seperate PersianDate's field and also Parse the DateString based
    // on this delimiter
    var delimiter = "/"

    private fun convertToMilis(julianDate: Long): Long {
        return PersianCalendarConstants.MILLIS_JULIAN_EPOCH + julianDate * PersianCalendarConstants.MILLIS_OF_A_DAY + PersianCalendarUtils.ceil(
            (timeInMillis - PersianCalendarConstants.MILLIS_JULIAN_EPOCH).toDouble(),
            PersianCalendarConstants.MILLIS_OF_A_DAY.toDouble()
        )
    }

    /**
     * default constructor
     *
     * most of the time we don't care about TimeZone when we persisting Date or
     * doing some calculation on date. ** Default TimeZone was set to
     * "GMT" ** in order to make developer to work more convenient with
     * the library; however you can change the TimeZone as you do in
     * GregorianCalendar by calling setTimeZone()
     */
    constructor(millis: Long) {
        timeInMillis = millis
    }

    /**
     * default constructor
     *
     * most of the time we don't care about TimeZone when we persisting Date or
     * doing some calculation on date. ** Default TimeZone was set to
     * "GMT" ** in order to make developer to work more convenient with
     * the library; however you can change the TimeZone as you do in
     * GregorianCalendar by calling setTimeZone()
     */
    constructor() : super(TimeZone.getDefault(), Locale.getDefault()) {}

    /**
     * Calculate persian date from current Date and populates the corresponding
     * fields(persianYear, persianMonth, persianDay)
     */
    protected fun calculatePersianDate() {
        val persianYearMonthDay = gregorianToJalali(
            YearMonthDay(
                this[YEAR], this[MONTH], this[DAY_OF_MONTH]
            )
        )
        persianYear = persianYearMonthDay.year
        persianMonth = persianYearMonthDay.month
        persianDay = persianYearMonthDay.day
    }// calculatePersianDate();

    /**
     *
     * Determines if the given year is a leap year in persian calendar. Returns
     * true if the given year is a leap year.
     *
     * @return boolean
     */
    val isPersianLeapYear: Boolean
        get() =// calculatePersianDate();
            PersianDateImpl.isLeapYear(persianYear)

    /**
     * set the persian date it converts PersianDate to the Julian and assigned
     * equivalent milliseconds to the instance
     *
     * @param persianYear
     * @param persianMonth
     * @param persianDay
     */
    fun setPersianDate(persianYear: Int, persianMonth: Int, persianDay: Int) {
        this.persianYear = persianYear
        this.persianMonth = persianMonth
        this.persianDay = persianDay
        val gregorianYearMonthDay =
            persianToGregorian(YearMonthDay(persianYear, this.persianMonth - 1, persianDay))
        this[gregorianYearMonthDay.year, gregorianYearMonthDay.month] = gregorianYearMonthDay.day
    }

    /**
     *
     * @return int persian month number
     */
    fun getPersianMonth(): Int {
        // calculatePersianDate();
        return persianMonth + 1
    }// calculatePersianDate();

    /**
     *
     * @return String persian month name
     */
    val persianMonthName: String
        get() =// calculatePersianDate();
            PersianCalendarConstants.persianMonthNames[persianMonth]

    /**
     *
     * @return String Name of the day in week
     */
    val persianWeekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> PersianCalendarConstants.persianWeekDays[0]
            SUNDAY -> PersianCalendarConstants.persianWeekDays[1]
            MONDAY -> PersianCalendarConstants.persianWeekDays[2]
            TUESDAY -> PersianCalendarConstants.persianWeekDays[3]
            WEDNESDAY -> PersianCalendarConstants.persianWeekDays[4]
            THURSDAY -> PersianCalendarConstants.persianWeekDays[5]
            else -> PersianCalendarConstants.persianWeekDays[6]
        }

    /**
     *
     * @return String of Persian Date ex: شنبه 01 خرداد 1361
     */
    val persianLongDate: String
        get() = persianWeekDayName + "  " + persianDay + "  " + persianMonthName + "  " + persianYear
    val persianLongDateAndTime: String
        get() = persianLongDate + " ساعت " + get(HOUR_OF_DAY) + ":" + get(MINUTE) + ":" + get(
            SECOND
        )// calculatePersianDate();

    /**
     *
     * @return String of persian date formatted by
     * 'YYYY[delimiter]mm[delimiter]dd' default delimiter is '/'
     */
    val persianShortDate: String
        get() =// calculatePersianDate();
            "" + formatToMilitary(persianYear) + delimiter + formatToMilitary(getPersianMonth()) + delimiter + formatToMilitary(
                persianDay
            )

    val persianShortDateTime: String
        get() = ("" + formatToMilitary(persianYear) + delimiter + formatToMilitary(getPersianMonth()) + delimiter + formatToMilitary(
            persianDay
        ) + " " + formatToMilitary(
            this[HOUR_OF_DAY]
        ) + ":" + formatToMilitary(get(MINUTE))
                + ":" + formatToMilitary(get(SECOND)))

    private fun formatToMilitary(i: Int): String {
        return if (i < 9) "0$i" else i.toString()
    }

    /**
     * add specific amout of fields to the current date for now doesnt handle
     * before 1 farvardin hejri (before epoch)
     *
     * @param field
     * @param amount
     * <pre>
     * Usage:
     * `addPersianDate(Calendar.YEAR, 2);
     * addPersianDate(Calendar.MONTH, 3);
    ` *
    </pre> *
     *
     * u can also use Calendar.HOUR_OF_DAY,Calendar.MINUTE,
     * Calendar.SECOND, Calendar.MILLISECOND etc
     */
    //
    fun addPersianDate(field: Int, amount: Int) {
        if (amount == 0) {
            return  // Do nothing!
        }
        require(!(field < 0 || field >= ZONE_OFFSET))
        if (field == YEAR) {
            setPersianDate(persianYear + amount, getPersianMonth(), persianDay)
            return
        } else if (field == MONTH) {
            setPersianDate(
                persianYear + (getPersianMonth() + amount) / 12, (getPersianMonth() + amount) % 12,
                persianDay
            )
            return
        }
        add(field, amount)
        calculatePersianDate()
    }

    /**
     * <pre>
     * use `[PersianDateParser]` to parse string
     * and get the Persian Date.
    </pre> *
     *
     * @see PersianDateParser
     *
     * @param dateString
     */
    fun parse(dateString: String?) {
        val p: PersianCalendar = PersianDateParser(dateString, delimiter).persianDate
        setPersianDate(p.persianYear, p.getPersianMonth(), p.persianDay)
    }

    override fun toString(): String {
        val str = super.toString()
        return str.substring(0, str.length - 1) + ",PersianDate=" + persianShortDate + "]"
    }

    override fun equals(obj: Any?): Boolean {
        return super.equals(obj)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun set(field: Int, value: Int) {
        super.set(field, value)
        calculatePersianDate()
    }

    override fun setTimeInMillis(millis: Long) {
        super.setTimeInMillis(millis)
        calculatePersianDate()
    }

    override fun setTimeZone(zone: TimeZone) {
        super.setTimeZone(zone)
        calculatePersianDate()
    }

    internal class YearMonthDay(var year: Int, var month: Int, var day: Int) {

        override fun toString(): String {
            return "$year/$month/$day"
        }
    }

    companion object {
        private const val serialVersionUID = 5541422440580682494L

        // Helper Functions
        private val gregorianDaysInMonth = intArrayOf(
            31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31
        )
        private val persianDaysInMonth = intArrayOf(
            31, 31, 31, 31, 31, 31, 30, 30,
            30, 30, 30, 29
        )

        private fun gregorianToJalali(gregorian: YearMonthDay): YearMonthDay {
            require(!(gregorian.month > 11 || gregorian.month < -11))
            var persianYear: Int
            val persianMonth: Int
            val persianDay: Int
            var gregorianDayNo: Int
            var persianDayNo: Int
            val persianNP: Int
            var i: Int
            gregorian.year = gregorian.year - 1600
            gregorian.day = gregorian.day - 1
            gregorianDayNo =
                (365 * gregorian.year + Math.floor(((gregorian.year + 3) / 4).toDouble()).toInt()
                        - Math.floor(((gregorian.year + 99) / 100).toDouble()).toInt()
                        + Math.floor(((gregorian.year + 399) / 400).toDouble()).toInt())
            i = 0
            while (i < gregorian.month) {
                gregorianDayNo += gregorianDaysInMonth[i]
                ++i
            }
            if (gregorian.month > 1 && (gregorian.year % 4 == 0 && gregorian.year % 100 != 0
                        || gregorian.year % 400 == 0)
            ) {
                ++gregorianDayNo
            }
            gregorianDayNo += gregorian.day
            persianDayNo = gregorianDayNo - 79
            persianNP = Math.floor((persianDayNo / 12053).toDouble()).toInt()
            persianDayNo = persianDayNo % 12053
            persianYear = 979 + 33 * persianNP + 4 * (persianDayNo / 1461)
            persianDayNo = persianDayNo % 1461
            if (persianDayNo >= 366) {
                persianYear += Math.floor(((persianDayNo - 1) / 365).toDouble()).toInt()
                persianDayNo = (persianDayNo - 1) % 365
            }
            i = 0
            while (i < 11 && persianDayNo >= persianDaysInMonth[i]) {
                persianDayNo -= persianDaysInMonth[i]
                ++i
            }
            persianMonth = i
            persianDay = persianDayNo + 1
            return YearMonthDay(persianYear, persianMonth, persianDay)
        }

        private fun persianToGregorian(persian: YearMonthDay): YearMonthDay {
            require(!(persian.month > 11 || persian.month < -11))
            var gregorianYear: Int
            val gregorianMonth: Int
            val gregorianDay: Int
            var gregorianDayNo: Int
            var persianDayNo: Int
            var leap: Int
            var i: Int
            persian.year = persian.year - 979
            persian.day = persian.day - 1
            persianDayNo =
                365 * persian.year + (persian.year / 33) * 8 + Math.floor(((persian.year % 33 + 3) / 4).toDouble())
                    .toInt()
            i = 0
            while (i < persian.month) {
                persianDayNo += persianDaysInMonth[i]
                ++i
            }
            persianDayNo += persian.day
            gregorianDayNo = persianDayNo + 79
            gregorianYear = 1600 + 400 * Math.floor((gregorianDayNo / 146097).toDouble())
                .toInt() /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
            gregorianDayNo = gregorianDayNo % 146097
            leap = 1
            if (gregorianDayNo >= 36525) /* 36525 = 365*100 + 100/4 */ {
                gregorianDayNo--
                gregorianYear += 100 * Math.floor((gregorianDayNo / 36524).toDouble())
                    .toInt() /* 36524 = 365*100 + 100/4 - 100/100 */
                gregorianDayNo = gregorianDayNo % 36524
                if (gregorianDayNo >= 365) {
                    gregorianDayNo++
                } else {
                    leap = 0
                }
            }
            gregorianYear += 4 * Math.floor((gregorianDayNo / 1461).toDouble())
                .toInt() /* 1461 = 365*4 + 4/4 */
            gregorianDayNo = gregorianDayNo % 1461
            if (gregorianDayNo >= 366) {
                leap = 0
                gregorianDayNo--
                gregorianYear += Math.floor((gregorianDayNo / 365).toDouble()).toInt()
                gregorianDayNo = gregorianDayNo % 365
            }
            i = 0
            while (gregorianDayNo >= gregorianDaysInMonth[i] + if (i == 1 && leap == 1) i else 0) {
                gregorianDayNo -= gregorianDaysInMonth[i] + if (i == 1 && leap == 1) i else 0
                i++
            }
            gregorianMonth = i
            gregorianDay = gregorianDayNo + 1
            return YearMonthDay(gregorianYear, gregorianMonth, gregorianDay)
        }
    }
}