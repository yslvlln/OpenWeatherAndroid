package com.yslvlln.core.data

import com.google.firebase.auth.FirebaseAuth
import com.yslvlln.core.common.UNKNOWN_ERROR
import com.yslvlln.core.data.model.User
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

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

    override suspend fun signUp(email: String, password: String): Result<User> = try {
        tryFirebaseSignUp(email, password)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun tryFirebaseSignUp(email: String, password: String): Result<User> {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
        return if (authResult.isSuccessful) {
            val firebaseUser = authResult.result.user
            val domainUser = User(
                id = firebaseUser?.uid.orEmpty(),
                email = firebaseUser?.email.orEmpty(),
                userName = firebaseUser?.displayName
            )
            Result.success(domainUser)
        } else {
            Result.failure(authResult.exception?.cause ?: Exception(UNKNOWN_ERROR))
        }
    }
}