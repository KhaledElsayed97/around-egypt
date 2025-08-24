package dev.khaled.aroundegypt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.khaled.aroundegypt.utils.NetworkUtils
import dev.khaled.aroundegypt.domain.model.Experience
import dev.khaled.aroundegypt.domain.usecases.GetExperienceByIdUseCase
import dev.khaled.aroundegypt.domain.usecases.GetRecentExperiencesUseCase
import dev.khaled.aroundegypt.domain.usecases.GetRecommendedExperiencesUseCase
import dev.khaled.aroundegypt.domain.usecases.LikeExperienceUseCase
import dev.khaled.aroundegypt.domain.usecases.SearchExperiencesUseCase
import dev.khaled.aroundegypt.utils.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRecommendedExperiencesUseCase: GetRecommendedExperiencesUseCase,
    private val getRecentExperiencesUseCase: GetRecentExperiencesUseCase,
    private val searchExperiencesUseCase: SearchExperiencesUseCase,
    private val getExperienceByIdUseCase: GetExperienceByIdUseCase,
    private val likeExperienceUseCase: LikeExperienceUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel(), MainViewModelContract {

    private val _recommendedExperiences = MutableStateFlow<List<Experience>>(emptyList())
    override val recommendedExperiences: StateFlow<List<Experience>> = _recommendedExperiences.asStateFlow()

    private val _recentExperiences = MutableStateFlow<List<Experience>>(emptyList())
    override val recentExperiences: StateFlow<List<Experience>> = _recentExperiences.asStateFlow()

    private val _selectedExperience = MutableStateFlow<Experience?>(null)
    override val selectedExperience: StateFlow<Experience?> = _selectedExperience.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Experience>>(emptyList())
    override val searchResults: StateFlow<List<Experience>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    private val _isOffline = MutableStateFlow(false)
    override val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    init {
        observeNetworkState()
        loadExperiences()
    }

    private fun observeNetworkState() {
        networkUtils.observeNetworkState()
            .onEach { state ->
                _isOffline.value = state is NetworkState.Disconnected
            }
            .launchIn(viewModelScope)
    }

    override fun loadExperiences() {
        getRecommendedExperiencesUseCase()
            .onStart { _isLoading.value = true }
            .onEach { experiences ->
                _recommendedExperiences.value = experiences
                _error.value = null
            }
            .catch { exception ->
                _error.value = "Failed to load recommended experiences: ${exception.message}"
            }
            .launchIn(viewModelScope)

        getRecentExperiencesUseCase()
            .onStart { _isLoading.value = true }
            .onEach { experiences ->
                _recentExperiences.value = experiences
                _error.value = null
            }
            .catch { exception ->
                _error.value = "Failed to load recent experiences: ${exception.message}"
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            kotlinx.coroutines.delay(100)
            _isLoading.value = false
        }
    }

    override fun searchExperiences(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _error.value = null
            return
        }

        _isLoading.value = true
        _error.value = null

        searchExperiencesUseCase(query)
            .onEach { experiences ->
                _searchResults.value = experiences
                _error.value = null
            }
            .catch { exception ->
                _error.value = "Search failed: ${exception.message}"
                _searchResults.value = emptyList()
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            kotlinx.coroutines.delay(100)
            _isLoading.value = false
        }
    }

    override fun toggleLike(experience: Experience) {
        if (experience.isLiked != true) {
            likeExperienceUseCase(experience.id)
                .onEach { likesCount ->
                    updateExperienceLikeStatus(experience.id, likesCount)
                }
                .catch { exception ->
                    _error.value = "Failed to like experience: ${exception.message}"
                }
                .launchIn(viewModelScope)
        }
    }

    private fun updateExperienceLikeStatus(experienceId: String, likesCount: Int) {
        _selectedExperience.value?.let { experience ->
            if (experience.id == experienceId) {
                _selectedExperience.value = experience.copy(
                    isLiked = true,
                    likesCount = likesCount
                )
            }
        }

        _recommendedExperiences.value = _recommendedExperiences.value.map { experience ->
            if (experience.id == experienceId) {
                experience.copy(isLiked = true, likesCount = likesCount)
            } else {
                experience
            }
        }

        _recentExperiences.value = _recentExperiences.value.map { experience ->
            if (experience.id == experienceId) {
                experience.copy(isLiked = true, likesCount = likesCount)
            } else {
                experience
            }
        }
    }

    override fun selectExperience(experienceId: String?) {
        if (experienceId == null) {
            _selectedExperience.value = null
            return
        }

        getExperienceByIdUseCase(experienceId)
            .onEach { experience ->
                _selectedExperience.value = experience
                _error.value = null
            }
            .catch { exception ->
                _error.value = "Failed to load experience details: ${exception.message}"
            }
            .launchIn(viewModelScope)
    }

    fun clearSelection() {
        _selectedExperience.value = null
    }

    override fun clearError() {
        _error.value = null
    }
}