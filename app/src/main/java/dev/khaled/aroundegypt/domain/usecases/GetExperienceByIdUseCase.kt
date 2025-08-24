package dev.khaled.aroundegypt.domain.usecases

import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.domain.repository.ExperienceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExperienceByIdUseCase @Inject constructor(
    private val repository: ExperienceRepository
) {
    operator fun invoke(id: String): Flow<Experience> {
        return repository.getExperienceById(id)
    }
}

