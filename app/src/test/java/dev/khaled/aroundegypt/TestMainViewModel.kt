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
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _error.value = null
            return
        }
    }

    override fun loadExperiences() {
    }

    override fun toggleLike(experience: Experience) {
    }

    override fun clearError() {
        _error.value = null
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
        _error.value = null
    }

    fun setRecommendedExperiences(experiences: List<Experience>) {
        _recommendedExperiences.value = experiences
    }

    fun setRecentExperiences(experiences: List<Experience>) {
        _recentExperiences.value = experiences
    }

    fun setSearchResults(experiences: List<Experience>) {
        _searchResults.value = experiences
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setOffline(offline: Boolean) {
        _isOffline.value = offline
    }

    fun setError(errorMessage: String?) {
        _error.value = errorMessage
    }

    fun setNetworkState(networkState: dev.khaled.aroundegypt.utils.NetworkState) {
        _isOffline.value = networkState is dev.khaled.aroundegypt.utils.NetworkState.Disconnected
    }
}
