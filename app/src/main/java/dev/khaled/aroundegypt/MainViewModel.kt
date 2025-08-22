package dev.khaled.aroundegypt

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dev.khaled.aroundegypt.data.model.Sight

class MainViewModel {

    private val _recommendedSights = mutableStateListOf<Sight>()
    val recommendedSights: List<Sight> get() = _recommendedSights
    
    private val _mostRecentSights = mutableStateListOf<Sight>()
    val mostRecentSights: List<Sight> get() = _mostRecentSights

    private val _sights = mutableStateListOf<Sight>()
    val sights: List<Sight> get() = _sights
    
    private val _selectedSightId = mutableStateOf<String?>(null)
    val selectedSightId: State<String?> = _selectedSightId
    
    init {
        loadDummySights()
    }
    
    private fun loadDummySights() {
        val sights = listOf(
            Sight(
                id = "1",
                name = "Luxor",
                imageUrl = "https://i.postimg.cc/zXqPF7v1/Screenshot-3.png",
                likesCount = 128,
                description = "Great luxor.",
                isLiked = false
            ),
            Sight(
                id = "2",
                name = "Luxor",
                imageUrl = "https://i.postimg.cc/zXqPF7v1/Screenshot-3.png",
                likesCount = 128,
                description = "Great luxor.",
                isLiked = false
            ),
            Sight(
                id = "3",
                name = "Luxor",
                imageUrl = "https://i.postimg.cc/zXqPF7v1/Screenshot-3.png",
                likesCount = 128,
                description = "Great luxor.",
                isLiked = false
            ),
            Sight(
                id = "4",
                name = "Luxor",
                imageUrl = "https://i.postimg.cc/zXqPF7v1/Screenshot-3.png",
                likesCount = 128,
                description = "Great luxor.",
                isLiked = false
            ),
            Sight(
                id = "5",
                name = "Luxor",
                imageUrl = "https://i.postimg.cc/zXqPF7v1/Screenshot-3.png",
                likesCount = 128,
                description = "Great luxor.",
                isLiked = false
            ),
            Sight(
                id = "6",
                name = "Luxor",
                imageUrl = "https://i.postimg.cc/zXqPF7v1/Screenshot-3.png",
                likesCount = 128,
                description = "Great luxor.",
                isLiked = false
            )
        )
        
        _sights.addAll(sights)
        
        // Set recommended sights (first 3)
        _recommendedSights.addAll(sights.take(3))
        
        // Set most recent sights (next 3)
        _mostRecentSights.addAll(sights.take(6).drop(3))
    }
    
    fun toggleLike(sightId: String) {
        val index = _sights.indexOfFirst { it.id == sightId }
        if (index != -1) {
            val sight = _sights[index]

            if (!sight.isLiked) {
                val updatedSight = sight.copy(
                    isLiked = !sight.isLiked,
                    likesCount = sight.likesCount + 1
                )

                val newSights = _sights.toMutableList()
                newSights[index] = updatedSight
                _sights.clear()
                _sights.addAll(newSights)

                val recommendedIndex = _recommendedSights.indexOfFirst { it.id == sightId }
                if (recommendedIndex != -1) {
                    val newRecommended = _recommendedSights.toMutableList()
                    newRecommended[recommendedIndex] = updatedSight
                    _recommendedSights.clear()
                    _recommendedSights.addAll(newRecommended)
                }

                val recentIndex = _mostRecentSights.indexOfFirst { it.id == sightId }
                if (recentIndex != -1) {
                    val newRecent = _mostRecentSights.toMutableList()
                    newRecent[recentIndex] = updatedSight
                    _mostRecentSights.clear()
                    _mostRecentSights.addAll(newRecent)
                }
            }
        }
    }

    fun selectSight(sightId: String?) {
        _selectedSightId.value = sightId
    }
    
    fun getSightById(sightId: String): Sight? {
        return _sights.find { it.id == sightId }
    }
    
    fun clearSelection() {
        _selectedSightId.value = null
    }
}