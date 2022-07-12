package com.example.datepicker.utils

object PersianCalendarUtils {
    /**
     * Converts a provided Persian (Shamsi) date to the Julian Day Number (i.e.
     * the number of days since January 1 in the year 4713 BC). Since the
     * Persian calendar is a highly regular calendar, converting to and from a
     * Julian Day Number is not as difficult as it looks. Basically it's a
     * mather of dividing, rounding and multiplying. This routine uses Julian
     * Day Number 1948321 as focal point, since that Julian Day Number
     * corresponds with 1 Farvardin (1) 1.
     *
     * @param year
     * int persian year
     * @param month
     * int persian month
     * @param day
     * int persian day
     * @return long
     */
    fun persianToJulian(year: Long, month: Int, day: Int): Long {
        return 365L * (ceil(
            (year - 474L).toDouble(),
            2820.0
        ) + 474L - 1L) + Math.floor(
            (682L * (ceil(
                (year - 474L).toDouble(),
                2820.0
            ) + 474L) - 110L) / 2816.0
        ).toLong() + (PersianCalendarConstants.PERSIAN_EPOCH - 1L) + (1029983L
                * Math.floor((year - 474L) / 2820.0)
            .toLong()) + (if (month < 7) 31 * month else 30 * month + 6) + day
    }

    /**
     * Calculate whether current year is Leap year in persian or not
     *
     * @return boolean
     */
    fun isPersianLeapYear(persianYear: Int): Boolean {
        return ceil(
            (38.0 + (ceil((persianYear - 474L).toDouble(), 2820.0) + 474L)) * 682.0,
            2816.0
        ) < 682L
    }

    /**
     * Converts a provided Julian Day Number (i.e. the number of days since
     * January 1 in the year 4713 BC) to the Persian (Shamsi) date. Since the
     * Persian calendar is a highly regular calendar, converting to and from a
     * Julian Day Number is not as difficult as it looks. Basically it's a
     * mather of dividing, rounding and multiplying.
     *
     * @param julianDate
     * @return long
     */
    fun julianToPersian(julianDate: Long): Long {
        val persianEpochInJulian = julianDate - persianToJulian(475L, 0, 1)
        val cyear = ceil(persianEpochInJulian.toDouble(), 1029983.0)
        val ycycle =
            if (cyear != 1029982L) Math.floor((2816.0 * cyear.toDouble() + 1031337.0) / 1028522.0)
                .toLong() else 2820L
        val year = 474L + 2820L * Math.floor(persianEpochInJulian / 1029983.0).toLong() + ycycle
        val aux = 1L + julianDate - persianToJulian(year, 0, 1)
        val month =
            (if (aux > 186L) Math.ceil((aux - 6L).toDouble() / 30.0) - 1 else Math.ceil(aux.toDouble() / 31.0) - 1).toInt()
        val day = (julianDate - (persianToJulian(year, month, 1) - 1L)).toInt()
        return year shl 16 or (month shl 8).toLong() or day.toLong()
    }

    /**
     * Ceil function in original algorithm
     *
     * @param double1
     * @param double2
     * @return long
     */
    fun ceil(double1: Double, double2: Double): Long {
        return (double1 - double2 * Math.floor(double1 / double2)).toLong()
    }
}