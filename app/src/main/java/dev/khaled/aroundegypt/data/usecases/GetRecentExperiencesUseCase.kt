package dev.khaled.aroundegypt.data.usecases

import dev.khaled.aroundegypt.data.model.Experience
import dev.khaled.aroundegypt.data.repository.ExperienceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentExperiencesUseCase @Inject constructor(
    private val repository: ExperienceRepository
) {
    operator fun invoke(): Flow<List<Experience>> {
        return repository.getRecentExperiences()
    }
}
