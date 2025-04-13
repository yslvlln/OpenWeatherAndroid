package com.yslvlln.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 0,
    exportSchema = true
)
internal abstract class OpenWeatherDatabase: RoomDatabase() {
    // TODO add DAOs here
}