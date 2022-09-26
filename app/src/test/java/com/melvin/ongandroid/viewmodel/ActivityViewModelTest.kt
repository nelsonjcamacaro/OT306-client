package com.melvin.ongandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import com.melvin.ongandroid.model.InicioActivitys.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
        activityViewModel = ActivityViewModel(repository)
    }

    @Test
    fun `Given an activities list from mock ActivityRespository when View Model call load() slides LiveData should return an activities list`() = runTest {
        //Given
        coEvery {
            repository.getActivity(any())
        } returns activityViewModel.slides.postValue(fakeSuccesResponse.data.slides)

        //When
        activityViewModel.load()

        //Then
        assertEquals(activityViewModel.slides.value, fakeActivitiesList)
    }

    @Test
    fun `Given a null activities list from mock ActivityRespository when View Model call load() slides LiveData should return null`() = runTest {
        //Given
        coEvery {
            repository.getActivity(any())
        } returns activityViewModel.slides.postValue(null)

        //When
        activityViewModel.load()

        //Then
        assert(activityViewModel.slides.value.isNullOrEmpty())
    }

    @Test
    fun `Given an empty activities list from mock ActivityRespository when View Model call load() slides LiveData should return an empty activities list`() = runTest {
        //Given
        coEvery {
            repository.getActivity(any())
        } returns activityViewModel.slides.postValue(emptyList())

        //When
        activityViewModel.load()

        //Then
        assert(activityViewModel.slides.value!!.isEmpty())
    }

    // TODO `Given anResponse error at getActivities from mock ONGRespository when View Model is initialized some ViewModel Variable should return the error`()

    // TODO `Given Loading state at getActivities from mock ONGRespository when View Model is initialized some ViewModel Variable should return the state of network query`()

    companion object {
        val fakeActivitiesList = listOf<Activity>(
            Activity(
                description = "desripcion",
                name = "name",
                image = "image",
                id = 1
            ),
            Activity(
                description = "desripcion",
                name = "name",
                image = "image",
                id = 2
            )
        )
        val fakeSlides = Slides(fakeActivitiesList)
        val fakeSuccesResponse : RepositoryResponse<Slides> = RepositoryResponse(fakeSlides, Source.REMOTE)
    }

}