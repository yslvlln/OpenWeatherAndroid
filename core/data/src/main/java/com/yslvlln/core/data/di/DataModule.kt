package com.yslvlln.core.data.di

import com.yslvlln.core.data.repository.UserRepository
import com.yslvlln.core.data.repository.UserRepositoryImpl
import com.yslvlln.core.data.repository.WeatherRepository
import com.yslvlln.core.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    internal abstract fun bindsWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}