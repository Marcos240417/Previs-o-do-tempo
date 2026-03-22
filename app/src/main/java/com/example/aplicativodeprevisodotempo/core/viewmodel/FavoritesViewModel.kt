package com.example.aplicativodeprevisodotempo.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativodeprevisodotempo.core.data.repository.WeatherRepository
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: WeatherRepository) : ViewModel() {

    val favoriteCities: StateFlow<List<CityWithForecasts>> = repository.getFavoriteCities()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
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