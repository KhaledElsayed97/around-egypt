package dev.khaled.aroundegypt.domain.usecases

import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.domain.repository.ExperienceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentExperiencesUseCase @Inject constructor(
    private val repository: ExperienceRepository
) {
    operator fun invoke(): Flow<List<Experience>> {
        return repository.getRecentExperiences()
    }
}
