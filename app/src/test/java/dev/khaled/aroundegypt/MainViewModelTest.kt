package dev.khaled.aroundegypt

import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: TestMainViewModel
    private lateinit var testNetworkUtils: TestNetworkUtils
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testNetworkUtils = TestNetworkUtils(true)
        viewModel = TestMainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `setRecommendedExperiences should update recommended experiences`() = runTest {
        val testExperiences = listOf(
            Experience("1", "Pyramids", "test1", "description1", 100, false),
            Experience("2", "Luxor", "test2", "description2", 200, true)
        )

        viewModel.setRecommendedExperiences(testExperiences)

        assertEquals(testExperiences, viewModel.recommendedExperiences.value)
    }

    @Test
    fun `setRecentExperiences should update recent experiences`() = runTest {
        val testExperiences = listOf(
            Experience("1", "Pyramids", "test1", "description1", 100, false),
            Experience("2", "Luxor", "test2", "description2", 200, true)
        )

        viewModel.setRecentExperiences(testExperiences)

        assertEquals(testExperiences, viewModel.recentExperiences.value)
    }

    @Test
    fun `searchExperiences with blank query should clear results`() = runTest {
        viewModel.setSearchResults(
            listOf(
                Experience("1", "Pyramids", "test1", "description1", 100, false),
                Experience("2", "Luxor", "test2", "description2", 200, true)
            )
        )

        viewModel.searchExperiences("")

        assertEquals(emptyList<Experience>(), viewModel.searchResults.value)
        assertEquals(null, viewModel.error.value)
    }

    @Test
    fun `setSearchResults should update search results`() = runTest {
        val testExperiences = listOf(
            Experience("1", "Pyramids", "test1", "description1", 100, false),
            Experience("2", "Luxor", "test2", "description2", 200, true)
        )

        viewModel.setSearchResults(testExperiences)

        assertEquals(testExperiences, viewModel.searchResults.value)
    }


    @Test
    fun `searchExperiences with non-blank query should not clear results`() = runTest {
        val testExperiences = listOf(
            Experience("1", "Pyramids", "test1", "description1", 100, false),
            Experience("2", "Luxor", "test2", "description2", 200, true)
        )
        viewModel.setSearchResults(testExperiences)

        viewModel.searchExperiences("test query")

        assertEquals(testExperiences, viewModel.searchResults.value)
    }


    @Test
    fun `clearSearchResults should clear search results and error`() = runTest {
        viewModel.clearSearchResults()

        assertEquals(emptyList<Experience>(), viewModel.searchResults.value)
        assertEquals(null, viewModel.error.value)
    }


    @Test
    fun `network state changes should update offline state`() = runTest {
        viewModel.setNetworkState(NetworkState.Disconnected)

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.isOffline.value)
    }

    @Test
    fun `setLoading should update loading state`() = runTest {
        viewModel.setLoading(false)

        viewModel.setLoading(true)

        assertTrue(viewModel.isLoading.value)
    }


    @Test
    fun `setError should update error state`() = runTest {
        val errorMessage = "Network connection failed"

        viewModel.setError(errorMessage)

        assertEquals(errorMessage, viewModel.error.value)
    }

    @Test
    fun `setOffline should update offline state`() = runTest {
        viewModel.setOffline(false)

        viewModel.setOffline(true)

        assertTrue(viewModel.isOffline.value)
    }

    @Test
    fun `clearError should clear error state`() = runTest {
        viewModel.setError("Test error")

        viewModel.clearError()

        assertEquals(null, viewModel.error.value)
    }
}
