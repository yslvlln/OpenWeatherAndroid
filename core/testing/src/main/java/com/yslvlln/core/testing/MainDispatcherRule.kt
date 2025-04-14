package com.yslvlln.core.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * JUnit Test Rule to override the Main dispatcher with a TestDispatcher during unit tests.
 *
 * This is essential when testing ViewModels or any coroutine-based code that uses
 * `Dispatchers.Main`, which normally requires the Android runtime environment.
 *
 * By replacing the main dispatcher with a `TestDispatcher`, we ensure:
 * 1. Tests run without depending on Android framework components.
 * 2. Coroutine execution is controlled and deterministic, allowing us to use
 *    `advanceUntilIdle()`, `runCurrent()`, and other test control methods.
 *
 * `UnconfinedTestDispatcher` is the default, but you can inject a different dispatcher if needed.
 *
 * Usage:
 *
 * ```kotlin
 * @get:Rule
 * val mainDispatcherRule = MainDispatcherRule()
 * ```
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) = Dispatchers.setMain(testDispatcher)

    override fun finished(description: Description) = Dispatchers.resetMain()
}