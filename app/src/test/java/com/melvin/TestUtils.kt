package com.melvin

import com.melvin.ongandroid.utils.ResultState

class TestUtils {

    companion object {
        const val FAKE_LOADING_DELAY = 100L
        const val FAKE_LIST_SIZE = 5
        const val FAKE_ERROR_MESSAGE = "error message"
        val fakeLoadingResultState = ResultState.Loading<Nothing>()
        val fakeErrorResultState = ResultState.Error(Exception(FAKE_ERROR_MESSAGE))
    }

}