package com.example.diaryapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getWeekDays(): List<Pair<String, String>> {
    val calendar = Calendar.getInstance()
    val dateFormatDayName = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormatDayNumber = SimpleDateFormat("d", Locale.getDefault())

    // Aseguramos que comience desde el lunes
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

    // Generamos la lista de días de la semana
    return (0..6).map {
        val dayName = dateFormatDayName.format(calendar.time)
        val dayNumber = dateFormatDayNumber.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        Pair(dayName, dayNumber)
    }
}
