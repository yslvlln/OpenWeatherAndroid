package com.yslvlln.core.database.di

import com.yslvlln.core.database.OpenWeatherDatabase
import com.yslvlln.core.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesTopicsDao(
        database: OpenWeatherDatabase,
    ): WeatherDao = database.weatherDao()
}