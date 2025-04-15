package com.yslvlln.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.yslvlln.core.data.model.User
import kotlinx.coroutines.tasks.await
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

    override suspend fun signIn(email: String, password: String): Result<User> = try {
        tryFirebaseSignIn(email, password)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private suspend fun tryFirebaseSignUp(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val firebaseUser = authResult.user
            val domainUser = User(
                id = firebaseUser?.uid.orEmpty(),
                email = firebaseUser?.email.orEmpty(),
                userName = firebaseUser?.displayName
            )
            Result.success(domainUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun tryFirebaseSignIn(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            val firebaseUser = authResult.user
            val domainUser = User(
                id = firebaseUser?.uid.orEmpty(),
                email = firebaseUser?.email.orEmpty(),
                userName = firebaseUser?.displayName
            )
            Result.success(domainUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}