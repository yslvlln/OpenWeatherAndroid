package com.yslvlln.core.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
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
        val mockFirebaseUser = mockk<FirebaseUser> {
            coEvery { uid } returns "user123"
            coEvery { email } returns "test@example.com"
            coEvery { displayName } returns "Test User"
        }

        coEvery { firebaseAuth.currentUser } returns mockFirebaseUser

        userRepository.getUser()

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
}
