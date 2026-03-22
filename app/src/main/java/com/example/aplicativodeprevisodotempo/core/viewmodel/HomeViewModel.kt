package com.example.aplicativodeprevisodotempo.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativodeprevisodotempo.core.data.repository.WeatherRepository
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.core.data.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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