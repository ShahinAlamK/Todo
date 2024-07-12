package com.iconic.todos.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun dateFormat(date: Long?, pattern: String = "EEE MMM dd yyyy"): String? {
    if (date != null) {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
    return null


}