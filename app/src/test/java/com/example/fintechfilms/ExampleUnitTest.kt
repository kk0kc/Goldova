package com.example.fintechfilms

import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.fintechfilms.presentation.ListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.fintechfilms.data.FilmApi
import com.example.fintechfilms.response.top.Film
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.mockito.Mockito.*



@ExperimentalCoroutinesApi
class ExampleUnitTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var testDispatcher: TestCoroutineDispatcher

    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
    }

    @Test
    fun `getFilmsFromApi should update filmRep on successful API call`(): Unit = coroutineTestRule.runBlockingTest {
        val fragmentScenario = launchFragmentInContainer<ListFragment>()
        fragmentScenario.onFragment { fragment ->
            fragment.getFilmsFromApi()
            fragment.filmRep = listOf(Film(1,"1989","aaa","krgmr"))
        }
        fragmentScenario.onFragment { fragment ->
            assertEquals(1, fragment.filmRep?.size)
        }
    }
}
