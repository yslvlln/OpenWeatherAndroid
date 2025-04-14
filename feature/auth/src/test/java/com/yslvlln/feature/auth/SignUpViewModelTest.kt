package com.yslvlln.feature.auth

import com.yslvlln.core.data.UserRepository
import com.yslvlln.core.data.model.User
import com.yslvlln.core.testing.MainDispatcherRule
import com.yslvlln.feature.auth.state.SignUpUiState
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
class SignUpViewModelTest {

    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: SignUpViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = SignUpViewModel(userRepository)
    }

    @Test
    fun `signUp emits Loading then Success when repository succeeds`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val user = User(id = "123", email = email, userName = "test")

        coEvery { userRepository.signUp(email, password) } returns Result.success(user)

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.signUp(email, password)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertIs<SignUpUiState.Success>(currentState)
        assertEquals(user, currentState.user)
    }

    @Test
    fun `signUp emits Loading then Error when repository fails`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val exception = Exception("Sign up failed")

        coEvery { userRepository.signUp(email, password) } returns Result.failure(exception)

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.signUp(email, password)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is SignUpUiState.Error)
        assertEquals("Sign up failed", (state as SignUpUiState.Error).message)
    }
}