package com.yslvlln.feature.weather.util

import android.annotation.SuppressLint
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
fun Long.toFormattedTime(pattern: String = "h:mm a"): String {
    val instant = Instant.fromEpochSeconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return localDateTime.toJavaLocalDateTime().format(formatter)
}