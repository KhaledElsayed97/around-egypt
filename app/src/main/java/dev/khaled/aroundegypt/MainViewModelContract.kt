package dev.khaled.aroundegypt

import dev.khaled.aroundegypt.domain.model.Experience
import kotlinx.coroutines.flow.StateFlow

interface MainViewModelContract {
    val recommendedExperiences: StateFlow<List<Experience>>
    val recentExperiences: StateFlow<List<Experience>>
    val selectedExperience: StateFlow<Experience?>
    val searchResults: StateFlow<List<Experience>>
    val isLoading: StateFlow<Boolean>
    val isOffline: StateFlow<Boolean>
    val error: StateFlow<String?>

    fun selectExperience(experienceId: String?)
    fun searchExperiences(query: String)
    fun loadExperiences()
    fun toggleLike(experience: Experience)
    fun clearError()
}
