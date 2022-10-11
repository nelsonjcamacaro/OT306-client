package com.melvin.ongandroid.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.MainDispatcherRule
import com.google.common.base.CharMatcher.any
import com.melvin.ongandroid.model.nosotrosActivities.MembersRepository
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.utils.ResultState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MembersViewModelTest {

    private lateinit var viewModel: MembersViewModel

    @RelaxedMockK
    private lateinit var repository: MembersRepository

    @get: Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun onBefore() {

        MockKAnnotations.init(this)
        viewModel = MembersViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given the member response, when members results are being loaded, then the member list return a member `() = runTest {
        //given
        val membersList = flow { emit(ResultState.Success(listOf(fakeMembersList))) }
        coEvery { repository.getMembersResponse() } returns membersList

        //when
        viewModel.loadMembersResult()
        //then
        assert(viewModel.membersResultState.value == membersList.first())
    }

    @Test
    fun `when the members response is a list of null then return a null`() = runTest {
        //given
        val membersList = flow { emit(ResultState.Success(listOf(null))) }
        coEvery { repository.getMembersResponse() } returns membersList

        //when
        viewModel.loadMembersResult()
        //then
        assert(viewModel.membersResultState.value == membersList.first())
    }

    @Test
    fun `when the members response returns any object then return whatever`() = runTest {
        //given
        val membersList = flow { emit(ResultState.Success(any())) }
        coEvery { repository.getMembersResponse() } returns membersList

        //when
        viewModel.loadMembersResult()
        //then
        assert(viewModel.membersResultState.value == membersList.first())
    }

    @Test
    fun `when the response is only a String then set that value to the live data`() = runTest {
        //given
        val membersList = flow { emit(ResultState.Success(String)) }
        coEvery { repository.getMembersResponse() } returns membersList

        //when
        viewModel.loadMembersResult()
        //then
        assert(viewModel.membersResultState.value == membersList.last())
    }


    companion object {
        val fakeMembersList = listOf<MemberDto>(
            MemberDto(
                -1,
                "fakeName1",
                "urlFake",
                "fake description",
                "urlfake",
                "urlfake",
                "",
                "",
                "",
                ""
            ), MemberDto(
                -2,
                "fakeName2",
                "urlFake2",
                "fake description2",
                "urlfake2",
                "urlfake2",
                "",
                "",
                "",
                ""
            )
        )
    }
}



