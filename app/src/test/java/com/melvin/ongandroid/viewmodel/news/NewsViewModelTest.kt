package com.melvin.ongandroid.viewmodel.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import com.melvin.TestUtils.Companion.FAKE_ERROR_MESSAGE
import com.melvin.TestUtils.Companion.FAKE_LIST_SIZE
import com.melvin.ongandroid.businesslogic.news.GetNewsUseCase
import com.melvin.ongandroid.model.news.NewsModel
import com.melvin.ongandroid.model.news.NewsResponse
import com.melvin.ongandroid.model.news.NewsViewState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @RelaxedMockK
    private lateinit var newsUseCase: GetNewsUseCase

    private lateinit var newsViewModel: NewsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun initMocksAndMainThread() {
        MockKAnnotations.init(this)
        newsViewModel = NewsViewModel(newsUseCase)
    }

    @Test
    fun `Given a Succes News Response with newsList from mock newsUseCase when loadNews is called in ViewModel newsData should return NewsViewState Succes with newsList as content`() =
        runTest {
            // Given
            coEvery {
                newsUseCase.execute()
            } returns fakeSuccesNewsResponse

            // When
            newsViewModel.loadNews()

            // Then
            assertEquals(
                fakeSuccesNewsViewState,
                newsViewModel.newsData.value
            )
        }

    @Test
    fun `Given a Succes News Response with an emptyList() from mock newsUseCase when loadNews is called in ViewModel newsData should return NewsViewState Succes with emptyList() as content`() =
        runTest {
            // Given
            coEvery {
                newsUseCase.execute()
            } returns NewsResponse(data = emptyList())

            // When
            newsViewModel.loadNews()

            // Then
            assertEquals(
                fakeSuccesNewsViewState,
                newsViewModel.newsData.value
            )
        }

    @Test
    fun `Given an Error News Response with message from mock newsUseCase when loadNews is called in ViewModel newsData should return NewsViewState Error with an Exception`() =
        runTest {
            // Given
            coEvery {
                kotlin.runCatching {
                    newsUseCase.execute()
                }
            } returns kotlin.runCatching {
                throw(Exception(FAKE_ERROR_MESSAGE))
            }

            // When
            newsViewModel.loadNews()

            // Then
            assertEquals(
                fakeErrorNewsViewState,
                newsViewModel.newsData.value
            )
        }

    companion object {
        private val fakeNewsList = buildList<NewsModel> {
            repeat(FAKE_LIST_SIZE) {
                NewsModel(
                    name = "Name $it"
                )
            }
        }
        val fakeSuccesNewsResponse = NewsResponse(data = fakeNewsList)
        val fakeSuccesNewsViewState = NewsViewState.Content(fakeSuccesNewsResponse.data)
        val fakeErrorNewsViewState = NewsViewState.Error(error = Exception(FAKE_ERROR_MESSAGE))
    }

}