package com.example.picker.utils

import com.example.picker.date.PersianDateImpl

class PersianDateParser
/**
 * <pre>
 * construct parser with date string assigned
 * the default delimiter is '/'.
 *
 * To assign deferment delimiter use:
 * [.PersianDateParser]
 *
 * Example
 *
 * `PersianCalendar pCal =
 * new PersianDateParser("1361/3/1").getPersianDate();
` *
</pre> *
 *
 * @param dateString
 */(var dateString: String?) {
    var delimiter = "/"

    /**
     * <pre>
     * construct parser with date string assigned
     * the default delimiter is '/'. with this constructor
     * you can set different delimiter to parse the date
     * based on this delimiter.
     * see also:
     * [.PersianDateParser]
     *
     * Example
     *
     * `PersianCalendar pCal =
     * new PersianDateParser("1361-3-1","-").getPersianDate();
    ` *
    </pre> *
     *
     * @param dateString
     * @param delimiter
     */
    constructor(dateString: String?, delimiter: String) : this(dateString) {
        this.delimiter = delimiter
    }

    /**
     * Produce the PersianCalendar object from given DateString throws Exception
     * if couldn't parse the text.
     *
     * @return PersianCalendar object
     * @exception RuntimeException
     */
    val persianDate: PersianCalendar
        get() {
            checkDateStringInitialValidation()
            val tokens = splitDateString(
                normalizeDateString(
                    dateString
                )
            )
            val year = tokens[0].toInt()
            val month = tokens[1].toInt()
            val day = tokens[2].toInt()
            checkPersianDateValidation(year, month, day)
            val pCal = PersianCalendar()
            pCal.setPersianDate(year, month, day)
            return pCal
        }

    /**
     * validate the given date
     *
     * @param year
     * @param month
     * @param day
     */
    private fun checkPersianDateValidation(year: Int, month: Int, day: Int) {
        if (year < 1) throw RuntimeException("year is not valid")
        if (month < 1 || month > 12) throw RuntimeException("month is not valid")
        if (day < 1 || day > 31) throw RuntimeException("day is not valid")
        if (month > 6 && day == 31) throw RuntimeException("day is not valid")
        if (month == 12 && day == 30 && !PersianDateImpl.isLeapYear(year)) throw RuntimeException(
            "day is not valid $year is not a leap year"
        )
    }

    /**
     * planned for further calculation before parsing the text
     *
     * @param dateString
     * @return
     */
    private fun normalizeDateString(dateString: String?): String? {
        // dateString = dateString.replace("-", delimiter);
        return dateString
    }

    private fun splitDateString(dateString: String?): Array<String> {
        val tokens = dateString!!.split(delimiter).toTypedArray()
        if (tokens.size != 3) throw RuntimeException("wrong date:$dateString is not a Persian Date or can not be parsed")
        return tokens
    }

    private fun checkDateStringInitialValidation() {
        if (dateString == null) throw RuntimeException("input didn't assing please use setDateString()")
        // if(dateString.length()>10)
        // throw new RuntimeException("wrong date:" + dateString +
        // " is not a Persian Date or can not be parsed" );
    }

}
