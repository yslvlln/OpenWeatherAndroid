package com.yslvlln.feature.weather.util

fun Int.fahrenheitToCelsius(): Int {
    return ((this - 32) * 5 / 9)
}