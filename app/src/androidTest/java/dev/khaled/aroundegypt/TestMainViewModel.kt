package dev.khaled.aroundegypt

import dev.khaled.aroundegypt.domain.model.Experience
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TestMainViewModel : MainViewModelContract {
    private val _recommendedExperiences = MutableStateFlow<List<Experience>>(emptyList())
    override val recommendedExperiences: StateFlow<List<Experience>> = _recommendedExperiences

    private val _recentExperiences = MutableStateFlow<List<Experience>>(emptyList())
    override val recentExperiences: StateFlow<List<Experience>> = _recentExperiences

    private val _searchResults = MutableStateFlow<List<Experience>>(emptyList())
    override val searchResults: StateFlow<List<Experience>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading

    private val _isOffline = MutableStateFlow(false)
    override val isOffline: StateFlow<Boolean> = _isOffline

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error

    override fun searchExperiences(query: String) {
    }

    override fun loadExperiences() {
    }

    override fun toggleLike(experience: Experience) {
    }

    override fun clearError() {
        _error.value = null
    }

    fun setRecommendedExperiences(experiences: List<Experience>) {
        _recommendedExperiences.value = experiences
    }

    fun setRecentExperiences(experiences: List<Experience>) {
        _recentExperiences.value = experiences
    }
}
