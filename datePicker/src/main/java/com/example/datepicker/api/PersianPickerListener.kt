package com.example.datepicker.api

interface PersianPickerListener {
    fun onDateSelected(persianPickerDate: PersianPickerDate?)
    fun onDismissed()
}