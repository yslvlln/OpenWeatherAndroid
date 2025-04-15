package com.yslvlln.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val country: String,
    val temperature: Double,
    val weatherDescription: String,
    val sunrise: Long,
    val sunset: Long,
    val timestamp: Long
)