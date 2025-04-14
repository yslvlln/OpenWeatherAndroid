package com.yslvlln.core.data

import com.yslvlln.core.data.model.User

internal interface UserRepository {
    suspend fun getUser(): User?
}