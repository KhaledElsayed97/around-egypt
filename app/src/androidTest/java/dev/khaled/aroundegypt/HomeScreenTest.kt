package dev.khaled.aroundegypt

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.ui.screens.HomeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: TestMainViewModel

    private val mockExperiences = listOf(
        Experience(
            id = "1",
            title = "Test Experience 1",
            imageUrl = "test",
            description = "Test description 1",
            likesCount = 100,
            isLiked = false
        ),
        Experience(
            id = "2",
            title = "Test Experience 2",
            imageUrl = "test",
            description = "Test description 2",
            likesCount = 200,
            isLiked = true
        )
    )

    @Before
    fun setUp() {
        viewModel = TestMainViewModel()
    }

    @Test
    fun homeScreen_shouldDisplaySearchField() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToExperience = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Try Luxor").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplayRecommendedExperiences() {
        viewModel.setRecommendedExperiences(mockExperiences)

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToExperience = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Recommended Experiences").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Experience 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Experience 2").assertIsDisplayed()
    }

    @Test
    fun homeScreen_shouldDisplayRecentExperiences() {
        viewModel.setRecentExperiences(mockExperiences)

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToExperience = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Most Recent").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Experience 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Experience 2").assertIsDisplayed()
    }

    @Test
    fun homeScreenSearch_whenTyping_shouldDisplayClearIcon() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToExperience = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Try Luxor").performTextInput("Test Experience 1")
        composeTestRule.onNodeWithContentDescription("Clear search").assertIsDisplayed()
    }

    @Test
    fun homeScreen_whenClearIconClicked_shouldClearSearch() {
        composeTestRule.setContent {
            HomeScreen(
                onNavigateToExperience = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Try Luxor").performTextInput("Test Experience 1")
        composeTestRule.onNodeWithContentDescription("Clear search").performClick()
        composeTestRule.onNodeWithContentDescription("Clear search").assertIsNotDisplayed()
    }

    @Test
    fun homeScreen_whenSearchCleared_shouldShowExperiences() {
        viewModel.setRecommendedExperiences(listOf(mockExperiences[0]))
        viewModel.setRecentExperiences(listOf(mockExperiences[1]))

        composeTestRule.setContent {
            HomeScreen(
                onNavigateToExperience = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Try Luxor").performTextInput("Test Experience 1")
        composeTestRule.onNodeWithContentDescription("Clear search").performClick()
        composeTestRule.onNodeWithContentDescription("Clear search").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Recommended Experiences").assertIsDisplayed()
        composeTestRule.onNodeWithText("Most Recent").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Experience 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Experience 2").assertIsDisplayed()
    }
}
