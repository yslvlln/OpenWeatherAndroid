package com.yslvlln.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Dummy::class],
    version = 1,
    exportSchema = true
)
internal abstract class OpenWeatherDatabase: RoomDatabase() {
    // TODO add DAOs here
}