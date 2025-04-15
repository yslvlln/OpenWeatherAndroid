package com.yslvlln.feature.auth

import com.yslvlln.core.data.UserRepository
import com.yslvlln.core.data.model.User
import com.yslvlln.core.testing.MainDispatcherRule
import com.yslvlln.feature.auth.state.SignInUiState
import com.yslvlln.feature.auth.screens.signin.SignInViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SignInViewModelTest {

    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: SignInViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = SignInViewModel(userRepository)
    }

    @Test
    fun `signIn emits Loading then Success when repository succeeds`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val user = User(id = "123", email = email, userName = "test")

        coEvery { userRepository.signIn(email, password) } returns Result.success(user)

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.signIn(email, password)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertIs<SignInUiState.Success>(currentState)
        assertEquals(user, currentState.user)
    }

    @Test
    fun `signIn emits Loading then Error when repository fails`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val exception = Exception("Sign up failed")

        coEvery { userRepository.signIn(email, password) } returns Result.failure(exception)

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.signIn(email, password)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is SignInUiState.Error)
        assertEquals("Sign up failed", (state as SignInUiState.Error).message)
    }
}