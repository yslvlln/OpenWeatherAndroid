package com.yslvlln.core.data.di

import com.yslvlln.core.data.UserRepository
import com.yslvlln.core.data.UserRepositoryImpl
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
}