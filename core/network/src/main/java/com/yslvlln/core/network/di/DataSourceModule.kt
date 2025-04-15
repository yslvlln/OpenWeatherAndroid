package com.yslvlln.core.network.di

import com.yslvlln.core.network.data.WeatherRemoteSource
import com.yslvlln.core.network.data.WeatherRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    internal abstract fun bindsWeatherRemoteSource(
        weatherRemoteSourceImpl: WeatherRemoteSourceImpl
    ): WeatherRemoteSource

}
