package com.yslvlln.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yslvlln.core.database.OpenWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "open-weather-db"

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesOpenWeatherDatabase(
        @ApplicationContext context: Context
    ): OpenWeatherDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = OpenWeatherDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }
}