package com.example.aplicativodeprevisodotempo.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativodeprevisodotempo.data.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: WeatherRepository) : ViewModel() {

    val favoriteCities: StateFlow<List<CityWithForecasts>> = repository.getFavoriteCities()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleFavorite(cityName: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteStatus(cityName, isFavorite)
        }
    }

    fun refreshFavorites(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteCities.value.forEach { item ->
                repository.syncWeatherByCityName(item.city.cityName, apiKey)
            }
        }
    }
}