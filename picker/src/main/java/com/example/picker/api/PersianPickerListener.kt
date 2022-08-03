package com.example.picker.api

interface PersianPickerListener {
    fun onDateSelected(persianPickerDate: PersianPickerDate?)
    fun onDismissed()
}