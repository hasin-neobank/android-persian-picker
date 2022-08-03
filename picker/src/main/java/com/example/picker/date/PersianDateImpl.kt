package com.example.picker.date

import com.example.picker.api.PersianPickerDate
import java.util.*

class PersianDateImpl : PersianPickerDate {
    private var persianDate: PersianDateFixedLeapYear

    override fun setDate(timestamp: Long?) {
        persianDate = PersianDateFixedLeapYear(timestamp)
    }

    override fun setDate(date: Date?) {
        persianDate = PersianDateFixedLeapYear(date)
    }

    override fun setDate(persianYear: Int, persianMonth: Int, persianDay: Int) {
        try {
            persianDate.shYear = persianYear
            persianDate.shMonth = persianMonth
            persianDate.shDay = persianDay
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override val persianYear: Int
        get() = persianDate.shYear
    override val persianMonth: Int
        get() = persianDate.shMonth
    override val persianDay: Int
        get() = persianDate.shDay
    override val gregorianYear: Int
        get() = persianDate.grgYear
    override val gregorianMonth: Int
        get() = persianDate.grgMonth
    override val gregorianDay: Int
        get() = persianDate.grgDay
    override val dayOfWeek: Int
        get() = persianDate.dayOfWeek()
    override val persianMonthName: String
        get() = persianDate.monthName()
    override val persianDayOfWeekName: String
        get() = persianDate.dayName()
    override val persianLongDate: String
        get() = "$persianDayOfWeekName  $persianDay  $persianMonthName  $persianYear"
    override val gregorianDate: Date
        get() = persianDate.toDate()
    override val timestamp: Long
        get() = persianDate.time
    override val isLeapYear: Boolean
        get() = persianDate.isLeap

    companion object {
        fun isLeapYear(year: Int): Boolean {
            return PersianDateFixedLeapYear().isLeap(year)
        }
    }

    init {
        persianDate = PersianDateFixedLeapYear()
    }
}

