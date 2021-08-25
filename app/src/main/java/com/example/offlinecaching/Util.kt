package com.example.offlinecaching

import java.text.SimpleDateFormat
import java.util.*

fun getDate(unixTime: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = Date(unixTime)
    return dateFormat.format(date)
}

fun getTime(unixTime: Long): String {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val date = Date(unixTime)
    return timeFormat.format(date)
}