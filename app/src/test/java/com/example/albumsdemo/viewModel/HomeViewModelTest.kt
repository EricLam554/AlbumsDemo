package com.example.albumsdemo.viewModel

import com.example.albumsdemo.presentation.home.HomeViewModel
import com.example.albumsdemo.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import timber.log.Timber

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel()
    }

    @Test
    fun testGetAlbumsList() {
        mainCoroutineRule.runBlockingTest {
            viewModel.getAlbumsList()
        }
        Assert.assertTrue(viewModel.albumsListItemViewModelsLiveData.value.isNullOrEmpty())
    }


}