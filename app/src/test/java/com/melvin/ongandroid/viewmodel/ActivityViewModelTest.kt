package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import com.melvin.TestUtils.Companion.FAKE_LIST_SIZE
import com.melvin.TestUtils.Companion.FAKE_LOADING_DELAY
import com.melvin.TestUtils.Companion.fakeErrorResultState
import com.melvin.TestUtils.Companion.fakeLoadingResultState
import com.melvin.ongandroid.model.inicioActivitys.*
import com.melvin.ongandroid.utils.ResultState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ActivityViewModelTest {

    @RelaxedMockK
    private lateinit var repository: ActivityRepository

    private lateinit var activityViewModel: ActivityViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Given a flow that emit Loading and then Succes State with activities list from mock repository when ViewModel is initialize activitiesResultState should return first Loading and then Succes with activities list`() =
        runTest {
            // Given
            coEvery {
                repository.getActivity()
            } returns flow {
                emit(fakeLoadingResultState)
                delay(FAKE_LOADING_DELAY)
                emit(fakeSuccesActivitiesResultState)
            }

            // When
            activityViewModel = ActivityViewModel(repository)

            // Then
            assertEquals(
                fakeLoadingResultState,
                activityViewModel.activitiesResultState.value
            )

            delay(FAKE_LOADING_DELAY)
            // After fake loading delay
            assertEquals(
                fakeSuccesActivitiesResultState,
                activityViewModel.activitiesResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Succes Result State with a null activities list from mock repository when View Model is initialized activitiesResultState LiveData should return Result State Succes with null list`() =
        runTest {
            // Given
            coEvery {
                repository.getActivity()
            } returns flow { emit(ResultState.Success(listOf(null))) }

            // When
            activityViewModel = ActivityViewModel(repository)

            // Then
            assertEquals(
                ResultState.Success(listOf(null)),
                activityViewModel.activitiesResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Succes Result State with an empty activities list from mock repository when View Model is initialized activitiesResultState LiveData should return Result State Succes with an empty list`() =
        runTest {
            //Given
            coEvery {
                repository.getActivity()
            } returns flow { emit(ResultState.Success(emptyList<Any>())) }

            //When
            activityViewModel = ActivityViewModel(repository)

            //Then
            assertEquals(
                ResultState.Success(emptyList<Any>()),
                activityViewModel.activitiesResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Loading and then Error with message from mock repository when ViewModel is initialize activitiesResultState should return first Loading and then Error with message`() =
        runTest {
            // Given
            coEvery {
                repository.getActivity()
            } returns flow {
                emit(fakeLoadingResultState)
                delay(FAKE_LOADING_DELAY)
                emit(fakeErrorResultState)
            }

            // When
            activityViewModel = ActivityViewModel(repository)

            // Then
            assertEquals(
                fakeLoadingResultState,
                activityViewModel.activitiesResultState.value
            )

            delay(FAKE_LOADING_DELAY)
            // After fake loading delay
            assertEquals(
                fakeErrorResultState,
                activityViewModel.activitiesResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Loading Result State at getActivity from mock repository when View Model is initialized activitiesResultState Live Data should return Result State Loading`() =
        runTest {
            // Given
            coEvery {
                repository.getActivity()
            } returns flow { emit(fakeLoadingResultState) }

            // When
            activityViewModel = ActivityViewModel(repository)

            // Then
            assertEquals(
                fakeLoadingResultState,
                activityViewModel.activitiesResultState.value
            )
        }

    companion object {
        private val fakeActivitiesList = buildList<Activity> {
            repeat(FAKE_LIST_SIZE) {
                Activity(
                    description = "activity $it",
                    name = "Name $it"
                )
            }
        }
        private val fakeSlides = Slides(fakeActivitiesList)
        private val fakeSuccesActivitiesResultState: ResultState<Slides> =
            ResultState.Success(fakeSlides)
    }

}