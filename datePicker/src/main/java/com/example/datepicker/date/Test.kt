package com.example.datepicker.date

import saman.zamani.persiandate.PersianDate
import java.util.*

fun main(){
    val dateInstance = Date()
    val cal: Calendar = Calendar.getInstance()
    cal.time = dateInstance
    cal.add(Calendar.YEAR, -(9))
    val dateBefore18Years: Date = cal.time
    val currentPersianDate = PersianDate()
    val persianDate = PersianDate(dateBefore18Years)
    println("current persian date: ${currentPersianDate.shYear}/${currentPersianDate.shMonth}/${currentPersianDate.shDay}")
    println("18 years ago date: ${persianDate.shYear}/${persianDate.shMonth}/${persianDate.shDay}")
}