package com.example.offlinecaching

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

// returns date from Unix Epoch
fun getDate(unixTime: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = Date(unixTime)
    return dateFormat.format(date) // date formatting
}

// returns time from Unix Epoch
fun getTime(unixTime: Long): String {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val date = Date(unixTime)
    return timeFormat.format(date) // time formatting
}

// split the name of the area and the distance from the center
fun getPlace(placeText: String): List<String> {
    return placeText.split("of ") // splitting string
}

// return a color for background, based on the magnitude value
fun getMagColor(magnitude: Double?): Color {
    val magnitudeDouble = magnitude ?: return Color(android.graphics.Color.parseColor("#212121"))

    return when { // color generation conditionally
        magnitudeDouble >= 6 -> Color.Red.copy(alpha = 0.75f)
        magnitudeDouble >= 5 -> Color(android.graphics.Color.parseColor("#7f0000"))
        else -> Color(android.graphics.Color.parseColor("#212121"))
    }
}
