package com.yslvlln.core.data.repository

import com.yslvlln.core.data.model.User

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun signIn(email: String, password: String): Result<User>
}