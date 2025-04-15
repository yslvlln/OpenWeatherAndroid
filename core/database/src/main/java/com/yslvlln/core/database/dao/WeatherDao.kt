package com.yslvlln.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yslvlln.core.database.model.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    suspend fun getAllWeather(): List<WeatherEntity>
}