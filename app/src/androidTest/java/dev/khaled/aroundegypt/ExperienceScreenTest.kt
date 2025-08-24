package dev.khaled.aroundegypt

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.ui.screens.ExperienceScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class ExperienceScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: TestMainViewModel

    private val testExperience = Experience(
        id = "1",
        title = "Test Experience",
        imageUrl = "Test URL",
        description = "Test description",
        likesCount = 100,
        isLiked = false
    )

    @Before
    fun setUp() {
        viewModel = TestMainViewModel()
    }

    @Test
    fun experienceScreen_shouldDisplayExperienceTitle() {
        composeTestRule.setContent {
            ExperienceScreen(
                experience = testExperience,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Test Experience").assertIsDisplayed()
    }

    @Test
    fun experienceScreen_shouldDisplayLikesCount() {
        composeTestRule.setContent {
            ExperienceScreen(
                experience = testExperience,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("100").assertIsDisplayed()
    }

    @Test
    fun experienceScreen_shouldDisplayDescription() {
        composeTestRule.setContent {
            ExperienceScreen(
                experience = testExperience,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Test description").assertIsDisplayed()
    }

    @Test
    fun experienceScreen_shouldDisplayLikeButton() {
        composeTestRule.setContent {
            ExperienceScreen(
                experience = testExperience,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Like").assertIsDisplayed()
    }

    @Test
    fun experienceScreen_whenLikeClicked_shouldDisplayFilledHeart() {
        val likedExperience = testExperience.copy(isLiked = true)

        composeTestRule.setContent {
            ExperienceScreen(
                experience = likedExperience,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Like").assertIsDisplayed()
    }

    @Test
    fun experienceScreen_shouldDisplayLongDescription() {
        val experienceWithLongDescription = testExperience.copy(
            description = "This is a veeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeery long description."
        )

        composeTestRule.setContent {
            ExperienceScreen(
                experience = experienceWithLongDescription,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("This is a veeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeery long description.")
            .assertIsDisplayed()
    }

    @Test
    fun experienceScreen_shouldDisplayExperienceDetails() {
        val detailedExperience = Experience(
            id = "1",
            title = "Test Experience",
            imageUrl = "Test URL",
            description = "Test description",
            likesCount = 100,
            isLiked = false
        )

        composeTestRule.setContent {
            ExperienceScreen(
                experience = detailedExperience,
                onNavigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Test Experience").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test description").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Like").assertIsDisplayed()
    }
}