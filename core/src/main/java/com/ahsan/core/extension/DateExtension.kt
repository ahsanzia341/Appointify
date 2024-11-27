package com.ahsan.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatter(date: Date, format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun Date.toEasyFormat(): String {
    return formatter(this, "EEE, MMM dd hh:mm a")
}