package com.example.datepicker.date

import saman.zamani.persiandate.PersianDate
import java.util.*


class PersianDateFixedLeapYear : PersianDate {
    private val leapYears = listOf(
        1210, 1214, 1218, 1222, 1226, 1230, 1234, 1238, 1243, 1247, 1251, 1255, 1259, 1263,
        1267, 1271, 1276, 1280, 1284, 1288, 1292, 1296, 1300, 1304, 1309, 1313, 1317, 1321,
        1325, 1329, 1333, 1337, 1342, 1346, 1350, 1354, 1358, 1362, 1366, 1370, 1375, 1379,
        1383, 1387, 1391, 1395, 1399, 1403, 1408, 1412, 1416, 1420, 1424, 1428, 1432, 1436,
        1441, 1445, 1449, 1453, 1457, 1461, 1465, 1469, 1474, 1478, 1482, 1486, 1490, 1494,
        1498
    )

    constructor()
    constructor(timeInMilliSecond: Long?) : super(timeInMilliSecond)
    constructor(date: Date?) : super(date)

    override fun isLeap(year: Int): Boolean {
        return if (year > 1500) {
            super.isLeap(year)
        } else leapYears.contains(year)
    }
}