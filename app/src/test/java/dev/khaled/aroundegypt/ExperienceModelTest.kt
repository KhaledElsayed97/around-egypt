package dev.khaled.aroundegypt

import dev.khaled.aroundegypt.domain.model.Experience
import org.junit.Assert.assertEquals
import org.junit.Test

class ExperienceModelTest {

    @Test
    fun `Experience data class should have correct properties`() {
        val experience = Experience(
            id = "1",
            title = "Test Experience",
            imageUrl = "Test URL",
            description = "Test description",
            likesCount = 100,
            isLiked = false
        )

        assertEquals("1", experience.id)
        assertEquals("Test Experience", experience.title)
        assertEquals("Test URL", experience.imageUrl)
        assertEquals("Test description", experience.description)
        assertEquals(100, experience.likesCount)
        assertEquals(false, experience.isLiked)
    }
}
