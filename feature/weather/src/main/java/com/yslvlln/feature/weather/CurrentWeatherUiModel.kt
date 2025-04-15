package com.yslvlln.feature.weather

import androidx.annotation.DrawableRes

data class CurrentWeatherUiModel(
    val conditionTitle: String,
    val cityAndCountry: String,
    val temperature: String,
    val sunrise: String,
    val sunset: String,
    @DrawableRes val icon: Int,
)
