package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import com.melvin.ongandroid.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*
import retrofit2.Response

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
        testimonialsViewModel = TestimonialsViewModel(repository)
    }

    @Test
    fun `Given a testimonial list from mock ONGRespository when View Model is initialized testimoniasList LiveData should return a testimonial list`() = runTest {
        /** Given
        coEvery {
           repository.getTestimonialsList(any())
        } returns testimonialsViewModel.testimonialsList.postValue(fakeTestimonialsList)

        // When Testimonial ViewModel  is initialized

        // Then
        assertEquals(testimonialsViewModel.testimonialsList.value, fakeTestimonialsList) **/
    }

    @Test
    fun `Given a null testimonial list from mock ONGRespository when View Model is initialized testimoniasList LiveData should return null`() = runTest {
        /** Given
        coEvery {
            repository.getTestimonialsList(any())
        } returns testimonialsViewModel.testimonialsList.postValue(null)

        // When Testimonial ViewModel  is initialized

        // Then
        assert(testimonialsViewModel.testimonialsList.value.isNullOrEmpty()) **/
    }

    @Test
    fun `Given an empty testimonial list from mock ONGRespository when View Model is initialized testimoniasList LiveData should return an empty list`() = runTest {
        /** Given
        coEvery {
            repository.getTestimonialsList(any())
        } returns testimonialsViewModel.testimonialsList.postValue(emptyList())

        // When Testimonial ViewModel  is initialized

        // Then
        assert(testimonialsViewModel.testimonialsList.value!!.isEmpty())  **/
    }

    // TODO `Given anResponse error at getTestimonials from mock ONGRespository when View Model is initialized some ViewModel Variable should return the error`()

    // TODO `Given Loading state at getTestimonials from mock ONGRespository when View Model is initialized some ViewModel Variable should return the state of network query`()

    // TODO `Given a ultimasNovedades list from mock ONGRespository when View Model is initialized ultimasNovedades LiveData should return a testimonial list`()

    // TODO `Given a null ultimasNovedades list from mock ONGRespository when View Model is initialized ultimasNovedades LiveData should return null`()

    // TODO `Given an empty ultimasNovedades list from mock ONGRespository when View Model is initialized ultimasNovedades LiveData should return an empty list`()

    // TODO `Given anResponse error at getUltimasNovedades from mock ONGRespository when View Model is initialized some ViewModel Variable should return the error`()

    // TODO `Given Loading state at getUltimasNovedades from mock ONGRespository when View Model is initialized some ViewModel Variable should return the state of network query`()

    // TODO `Given anResponse error at getTestimonials from mock ONGRespository when View Model is initialized some ViewModel Variable should return the error`()

    // TODO `Given onResponse error at all queries from mock ONGRespository when View Model is initialized some ViewModel Variable should return the massive error`()

    companion object {
        val fakeTestimonialsList = buildList<Testimonial> {
            repeat(5) {
                Testimonial(
                    null,
                    null,
                    "testimony $it",
                    null,
                    null,
                    "https://thispersondoesnotexist.com/image",
                    "Name $it",
                    null
                )
            }
        }
        val fakeTestimonialsResponse = TestimonialsResponse(true, fakeTestimonialsList, "succes")
        val fakeResponse: Response<TestimonialsResponse> = Response.success(
            fakeTestimonialsResponse
        )
    }
}