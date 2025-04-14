package com.yslvlln.core.data

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yslvlln.core.common.UNKNOWN_ERROR
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        firebaseAuth = mockk<FirebaseAuth>()
        userRepository = UserRepositoryImpl(firebaseAuth)
    }

    @Test
    fun `getCurrentUser returns mapped User when Firebase user is not null`() = runTest {
        val mockFirebaseUser = getMockFirebaseUser()

        coEvery { firebaseAuth.currentUser } returns mockFirebaseUser

        val result = userRepository.getUser()

        assertEquals("user123", result?.id)
        assertEquals("test@example.com", result?.email)
        assertEquals("Test User", result?.userName)
    }

    @Test
    fun `getCurrentUser returns null when Firebase user is null`() = runTest {
        coEvery { firebaseAuth.currentUser } returns null

        val result = userRepository.getUser()

        assertNull(result)
    }
    
    @Test
    fun `signUp returns User when Firebase succeeds`() = runTest {
        val email = "test@example.com"
        val password = "securepassword"

        val firebaseUser = getMockFirebaseUser()

        val authResult = mockk<AuthResult> {
            coEvery { user } returns firebaseUser
        }

        val task = Tasks.forResult(authResult)

        coEvery { firebaseAuth.createUserWithEmailAndPassword(email, password) } returns task

        val result = userRepository.signUp(email, password)

        assertTrue(result.isSuccess)
        assertEquals("user123", result.getOrNull()?.id)
        assertEquals("test@example.com", result.getOrNull()?.email)
    }

    @Test
    fun `signUp returns failure when Firebase throws`() = runTest {
        val email = "fail@example.com"
        val password = "badpassword"

        coEvery {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        } returns Tasks.forException(Exception(UNKNOWN_ERROR))

        val result = userRepository.signUp(email, password)

        assertTrue(result.isFailure)
        assertEquals(UNKNOWN_ERROR, result.exceptionOrNull()?.message)
    }

    @Test
    fun `signIn returns User when Firebase succeeds`() = runTest {
        val email = "test@example.com"
        val password = "securepassword"

        val firebaseUser = getMockFirebaseUser()

        val authResult = mockk<AuthResult> {
            coEvery { user } returns firebaseUser
        }

        val task = Tasks.forResult(authResult)

        coEvery { firebaseAuth.signInWithEmailAndPassword(email, password) } returns task

        val result = userRepository.signIn(email, password)

        assertTrue(result.isSuccess)
        assertEquals("user123", result.getOrNull()?.id)
        assertEquals("test@example.com", result.getOrNull()?.email)
    }

    @Test
    fun `signIn returns failure when Firebase throws`() = runTest {
        val email = "fail@example.com"
        val password = "badpassword"

        coEvery {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        } returns Tasks.forException(Exception(UNKNOWN_ERROR))

        val result = userRepository.signIn(email, password)

        assertTrue(result.isFailure)
        assertEquals(UNKNOWN_ERROR, result.exceptionOrNull()?.message)
    }

    private fun getMockFirebaseUser(): FirebaseUser {
        val mockFirebaseUser = mockk<FirebaseUser> {
            coEvery { uid } returns "user123"
            coEvery { email } returns "test@example.com"
            coEvery { displayName } returns "Test User"
        }
        return mockFirebaseUser
    }
}
