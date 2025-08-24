package dev.khaled.aroundegypt

import dev.khaled.aroundegypt.data.local.datasource.ExperienceLocalDataSource
import dev.khaled.aroundegypt.data.remote.model.ExperienceResponse
import dev.khaled.aroundegypt.data.remote.repository.ExperienceRepositoryImpl
import dev.khaled.aroundegypt.data.remote.service.ExperienceApiService
import dev.khaled.aroundegypt.domain.model.Experience
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ExperienceRepositoryTest {

    @Mock
    private lateinit var apiService: ExperienceApiService

    @Mock
    private lateinit var localDataSource: ExperienceLocalDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchExperiences should throw exception when offline`() = runTest {
        val offlineNetworkUtils = TestNetworkUtils(false)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, offlineNetworkUtils)

        try {
            testRepository.searchExperiences("Luxor").first()
            assertTrue("Should throw exception when offline", false)
        } catch (e: Exception) {
            assertTrue(e.message?.contains("No network available") == true)
        }
    }

    @Test
    fun `searchExperiences should return results when online`() = runTest {
        val onlineNetworkUtils = TestNetworkUtils(true)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, onlineNetworkUtils)
        
        val mockExperiences = listOf(
            Experience(
                id = "1",
                title = "Luxor",
                imageUrl = "test",
                description = "description",
                likesCount = 100,
                isLiked = false
            )
        )
        
        whenever(apiService.searchExperiences("Luxor")).thenReturn(
            Response.success(ExperienceResponse(data = mockExperiences))
        )

        val result = testRepository.searchExperiences("Luxor")

        val experiences = result.first()
        assertEquals(1, experiences.size)
        assertEquals("Luxor Temple", experiences[0].title)
    }

    @Test
    fun `searchExperiences should throw exception on API error`() = runTest {
        val onlineNetworkUtils = TestNetworkUtils(true)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, onlineNetworkUtils)
        
        whenever(apiService.searchExperiences("Luxor")).thenThrow(RuntimeException("Network error"))

        try {
            testRepository.searchExperiences("Luxor").first()
            assertTrue("Should throw exception on API error", false)
        } catch (e: Exception) {
            assertTrue(e.message?.contains("Network error") == true)
        }
    }

    @Test
    fun `getExperienceById should throw exception when offline`() = runTest {
        val offlineNetworkUtils = TestNetworkUtils(false)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, offlineNetworkUtils)

        try {
            testRepository.getExperienceById("1").first()
            assertTrue("Should throw exception when offline", false)
        } catch (e: Exception) {
            assertTrue(e.message?.contains("No network available") == true)
        }
    }

    @Test
    fun `likeExperience should throw exception when offline`() = runTest {
        val offlineNetworkUtils = TestNetworkUtils(false)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, offlineNetworkUtils)

        try {
            testRepository.likeExperience("1").first()
            assertTrue("Should throw exception when offline", false)
        } catch (e: Exception) {
            assertTrue(e.message?.contains("No network available") == true)
        }
    }

    @Test
    fun `getRecommendedExperiences should return cached data when offline`() = runTest {
        val offlineNetworkUtils = TestNetworkUtils(false)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, offlineNetworkUtils)
        
        val cachedExperiences = listOf(
            Experience(
                id = "1",
                title = "Cached Experience",
                imageUrl = "test",
                description = "Cached description",
                likesCount = 100,
                isLiked = false
            )
        )
        
        whenever(localDataSource.getRecommendedExperiences()).thenReturn(
            flowOf(cachedExperiences.map { 
                dev.khaled.aroundegypt.data.local.room.entity.RecommendedExperienceEntity.fromExperience(it) 
            })
        )

        val result = testRepository.getRecommendedExperiences()

        val experiences = result.first()
        assertEquals(1, experiences.size)
        assertEquals("Cached Experience", experiences[0].title)
    }

    @Test
    fun `getRecentExperiences should return cached data when offline`() = runTest {
        val offlineNetworkUtils = TestNetworkUtils(false)
        val testRepository = ExperienceRepositoryImpl(apiService, localDataSource, offlineNetworkUtils)
        
        val cachedExperiences = listOf(
            Experience(
                id = "1",
                title = "Cached Experience",
                imageUrl = "test",
                description = "Cached description",
                likesCount = 100,
                isLiked = false
            )
        )
        
        whenever(localDataSource.getRecentExperiences()).thenReturn(
            flowOf(cachedExperiences.map { 
                dev.khaled.aroundegypt.data.local.room.entity.RecentExperienceEntity.fromExperience(it) 
            })
        )

        val result = testRepository.getRecentExperiences()

        val experiences = result.first()
        assertEquals(1, experiences.size)
        assertEquals("Cached Experience", experiences[0].title)
    }
}
