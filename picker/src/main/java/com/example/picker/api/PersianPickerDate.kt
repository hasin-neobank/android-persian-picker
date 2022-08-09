package com.example.picker.api

import java.util.*
/**
 * Thanks to <a href="https://github.com/aliab/Persian-Date-Picker-Dialog">Persian Date Picker Project</>
 */
interface PersianPickerDate {
    fun setDate(timestamp: Long?)
    fun setDate(date: Date?)
    fun setDate(persianYear: Int, persianMonth: Int, persianDay: Int)
    val persianYear: Int
    val persianMonth: Int
    val persianDay: Int
    val gregorianYear: Int
    val gregorianMonth: Int
    val gregorianDay: Int
    val dayOfWeek: Int
    val persianMonthName: String?
    val persianDayOfWeekName: String?
    /**
     * @return String of Persian Date ex: دوشنبه ۱۳ خرداد ۱۳۷۰
     */
    val persianLongDate: String?
    val gregorianDate: Date?
    val timestamp: Long
    val isLeapYear: Boolean
}