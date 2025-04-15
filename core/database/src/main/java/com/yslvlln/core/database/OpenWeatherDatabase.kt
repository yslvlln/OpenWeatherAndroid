package com.yslvlln.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yslvlln.core.database.dao.WeatherDao
import com.yslvlln.core.database.model.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = true
)
internal abstract class OpenWeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}