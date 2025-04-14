package com.yslvlln.core.data

import com.yslvlln.core.data.model.User

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun signUp(email: String, password: String): Result<User>
}