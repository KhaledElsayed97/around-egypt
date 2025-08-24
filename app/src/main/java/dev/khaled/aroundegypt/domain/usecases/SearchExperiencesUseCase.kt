package dev.khaled.aroundegypt.domain.usecases

import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.domain.repository.ExperienceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchExperiencesUseCase @Inject constructor(
    private val repository: ExperienceRepository
) {
    operator fun invoke(query: String): Flow<List<Experience>> {
        return repository.searchExperiences(query)
    }
}

