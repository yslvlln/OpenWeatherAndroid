package com.yslvlln.feature.weather.screens.currentWeather

import com.yslvlln.core.model.CurrentWeather
import com.yslvlln.feature.weather.util.fahrenheitToCelsius
import com.yslvlln.feature.weather.util.toFormattedTime

fun CurrentWeather?.toUiModel(): CurrentWeatherUiModel {
    return CurrentWeatherUiModel(
        conditionTitle = this?.cityName.orEmpty(),
        description = this?.weatherDescription.orEmpty(),
        cityAndCountry = "${this?.country.orEmpty()}, ${this?.cityName}",
        temperature = "${this?.temperature?.toInt()?.fahrenheitToCelsius()}°C",
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
