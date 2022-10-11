package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import com.melvin.TestUtils.Companion.FAKE_LIST_SIZE
import com.melvin.TestUtils.Companion.FAKE_LOADING_DELAY
import com.melvin.TestUtils.Companion.fakeErrorResultState
import com.melvin.TestUtils.Companion.fakeLoadingResultState
import com.melvin.ongandroid.model.*
import com.melvin.ongandroid.utils.ResultState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import java.util.Collections.emptyList

@ExperimentalCoroutinesApi
class TestimonialsViewModelTest {

    @RelaxedMockK
    private lateinit var repository: OngRepository

    private lateinit var testimonialsViewModel: TestimonialsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun initMocksAndMainThread() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Given a flow that emit Loading and then Succes State with testimonial list from mock repository when ViewModel is initialize testimonialResultState should return first Loading and then Succes with testimonial list`() =
        runTest {
            // Given
            coEvery {
                repository.getTestimonialsList()
            } returns flow {
                emit(fakeLoadingResultState)
                delay(FAKE_LOADING_DELAY)
                emit(fakeSuccesTestimonialResultState)
            }

            // When
            testimonialsViewModel = TestimonialsViewModel(repository)

            // Then
            assertEquals(
                fakeLoadingResultState,
                testimonialsViewModel.testimonialsResultState.value
            )

            delay(FAKE_LOADING_DELAY)
            // After fake loading delay
            assertEquals(
                fakeSuccesTestimonialResultState,
                testimonialsViewModel.testimonialsResultState.value
            )

        }

    @Test
    fun `Given a flow that emit Succes Result State with a null testimonial list from mock OngRepository when View Model is initialized testimonialsResultState LiveData should return Result State Succes with null list`() =
        runTest {
            // Given
            coEvery {
                repository.getTestimonialsList()
            } returns flow { emit(ResultState.Success(listOf(null))) }

            // When
            testimonialsViewModel = TestimonialsViewModel(repository)

            // Then
            assertEquals(
                ResultState.Success(listOf(null)),
                testimonialsViewModel.testimonialsResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Succes Result State with an empty testimonial list from mock OngRepository when View Model is initialized testimonialResultState LiveData should return a Result State Succes with an empty list`() =
        runTest {
            // Given
            coEvery {
                repository.getTestimonialsList()
            } returns flow { emit(ResultState.Success(emptyList<Any>())) }

            // When
            testimonialsViewModel = TestimonialsViewModel(repository)

            // Then
            assertEquals(
                ResultState.Success(emptyList<Any>()),
                testimonialsViewModel.testimonialsResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Loading and then Error with message from mock repository when ViewModel is initialize testimonialResultState should return first Loading and then Error with message`() =
        runTest {
            // Given
            coEvery {
                repository.getTestimonialsList()
            } returns flow {
                emit(fakeLoadingResultState)
                delay(FAKE_LOADING_DELAY)
                emit(fakeErrorResultState)
            }

            // When
            testimonialsViewModel = TestimonialsViewModel(repository)

            // First
            assertEquals(
                fakeLoadingResultState,
                testimonialsViewModel.testimonialsResultState.value
            )

            delay(FAKE_LOADING_DELAY)
            // After loading
            assertEquals(
                fakeErrorResultState,
                testimonialsViewModel.testimonialsResultState.value
            )
        }

    @Test
    fun `Given a flow that emit Loading Result State at getTestimonials from mock OngRepository when View Model is initialized testimonialsResultState Live Data should return Result State Loading`() =
        runTest {
            // Given
            coEvery {
                repository.getTestimonialsList()
            } returns flow {
                emit(fakeLoadingResultState)
            }

            // When
            testimonialsViewModel = TestimonialsViewModel(repository)

            // First
            assertEquals(
                fakeLoadingResultState,
                testimonialsViewModel.testimonialsResultState.value
            )
        }

    companion object {
        private val fakeTestimonialsList = buildList<Testimonial> {
            repeat(FAKE_LIST_SIZE) {
                Testimonial(
                    description = "testimony $it",
                    name = "Name $it"
                )
            }
        }
        val fakeSuccesTestimonialResultState: ResultState<Any> =
            ResultState.Success(fakeTestimonialsList)
    }
}