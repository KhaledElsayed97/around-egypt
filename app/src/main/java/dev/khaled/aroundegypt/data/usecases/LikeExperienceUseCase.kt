package dev.khaled.aroundegypt.data.usecases

import dev.khaled.aroundegypt.data.repository.ExperienceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeExperienceUseCase @Inject constructor(
    private val repository: ExperienceRepository
) {
    operator fun invoke(id: String): Flow<Int> {
        return repository.likeExperience(id)
    }
}
