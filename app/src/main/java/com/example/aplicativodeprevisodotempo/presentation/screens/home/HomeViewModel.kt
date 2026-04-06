package com.example.aplicativodeprevisodotempo.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativodeprevisodotempo.core.common.Constants
import com.example.aplicativodeprevisodotempo.data.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class HomeViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _currentLocationWeather = MutableStateFlow<CityWithForecasts?>(null)
    val currentLocationWeather = _currentLocationWeather.asStateFlow()

    val allCitiesWeather: StateFlow<List<CityWithForecasts>> = repository.getAllCitiesWithWeather()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val lastCityName: StateFlow<String?> = repository.getLastCitySynced()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Localizando..."
        )

    init {
        refreshLocationWeather()
    }

    fun refreshLocationWeather(lat: Double = -8.00, lon: Double = -35.03) {
        fetchWeatherByGps(lat, lon)
    }

    fun fetchWeatherByGps(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val latStr = String.format(Locale.US, "%.2f", lat)
                val lonStr = String.format(Locale.US, "%.2f", lon)
                val apiKey = Constants.API_KEY

                repository.syncWeatherData(latStr, lonStr, apiKey)

                repository.getCityWithForecastByCoords(latStr, lonStr, "São Lourenço da Mata")
                    .filterNotNull()
                    .collectLatest { data ->
                        _currentLocationWeather.value = data
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}