package com.yslvlln.core.data

import com.google.firebase.auth.FirebaseAuth
import com.yslvlln.core.data.model.User
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): UserRepository {

    override suspend fun getUser(): User? {
        val firebaseUser = firebaseAuth.currentUser
        return firebaseUser?.let {
            User(
                id = it.uid,
                email = it.email.orEmpty(),
                userName = it.displayName
            )
        }
    }
}