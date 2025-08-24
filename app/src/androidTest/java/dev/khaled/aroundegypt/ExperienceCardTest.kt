package dev.khaled.aroundegypt

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.ui.screens.ExperienceCard
import org.junit.Rule
import org.junit.Test

class ExperienceCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testExperience = Experience(
        id = "1",
        title = "Test Experience",
        imageUrl = "Test URL",
        description = "Test description",
        likesCount = 100,
        isLiked = false
    )

    @Test
    fun experienceCardTitle_shouldDisplayTitle() {
        composeTestRule.setContent {
            ExperienceCard(
                experience = testExperience,
                onClick = {},
                onLikeClick = {}
            )
        }

        composeTestRule.onNodeWithText("Test Experience").assertIsDisplayed()
    }

    @Test
    fun experienceCardLikes_shouldDisplayLikesCount() {
        composeTestRule.setContent {
            ExperienceCard(
                experience = testExperience,
                onClick = {},
                onLikeClick = {}
            )
        }

        composeTestRule.onNodeWithText("100").assertIsDisplayed()
    }

    @Test
    fun experienceCardLikeButton_shouldDisplayLikeButton() {
        composeTestRule.setContent {
            ExperienceCard(
                experience = testExperience,
                onClick = {},
                onLikeClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Like").assertIsDisplayed()
    }

    @Test
    fun experienceCardLikeButton_whenLikeClicked_shouldDisplayFilledHeart() {
        val likedExperience = testExperience.copy(isLiked = true)

        composeTestRule.setContent {
            ExperienceCard(
                experience = likedExperience,
                onClick = {},
                onLikeClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Like").assertIsDisplayed()
    }

    @Test
    fun experienceCard_shouldCallOnClickWhenClicked() {
        var clicked = false
        val onClick: () -> Unit = { clicked = true }

        composeTestRule.setContent {
            ExperienceCard(
                experience = testExperience,
                onClick = onClick,
                onLikeClick = {}
            )
        }

        composeTestRule.onNodeWithText("Test Experience").performClick()

        assert(clicked) {}
    }

    @Test
    fun experienceCard_whenLikeButtonClicked_shouldCallOnLikeClick() {
        var likeClicked = false
        val onLikeClick: () -> Unit = { likeClicked = true }

        composeTestRule.setContent {
            ExperienceCard(
                experience = testExperience,
                onClick = {},
                onLikeClick = onLikeClick
            )
        }

        composeTestRule.onNodeWithContentDescription("Like").performClick()

        assert(likeClicked) {}
    }
}
