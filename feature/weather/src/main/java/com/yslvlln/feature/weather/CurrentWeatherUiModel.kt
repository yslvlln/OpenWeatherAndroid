package com.yslvlln.feature.weather

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.yslvlln.core.model.CurrentWeather
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun CurrentWeather?.toUiModel(): CurrentWeatherUiModel {
    return CurrentWeatherUiModel(
        conditionTitle = this?.cityName.orEmpty(),
        description = this?.weatherDescription.orEmpty(),
        cityAndCountry = "${this?.country.orEmpty()}, ${this?.cityName}",
        temperature = "${this?.temperature?.toInt()}°C",
        sunrise = this?.sunrise?.toFormattedTime().orEmpty(),
        sunset = this?.sunset?.toFormattedTime().orEmpty()
    )
}

data class CurrentWeatherUiModel(
    val conditionTitle: String,
    val description: String,
    val cityAndCountry: String,
    val temperature: String,
    val sunrise: String,
    val sunset: String
)

@SuppressLint("NewApi")
fun Long.toFormattedTime(): String {
    val instant = Instant.fromEpochSeconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
    return localDateTime.toJavaLocalDateTime().format(formatter)
}
