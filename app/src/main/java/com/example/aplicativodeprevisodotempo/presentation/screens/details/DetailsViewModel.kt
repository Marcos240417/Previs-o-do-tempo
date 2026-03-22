package com.example.aplicativodeprevisodotempo.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativodeprevisodotempo.domain.repository.WeatherRepository
import com.example.aplicativodeprevisodotempo.core.common.Constants
import com.example.aplicativodeprevisodotempo.data.local.relations.ForecastWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadCityDetails(cityName: String) {

        _uiState.value = DetailsUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            launch {
                try {
                    repository.syncWeatherByCityName(cityName, Constants.API_KEY)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            repository.getForecastWithDetails(cityName)
                .collectLatest { detailsList ->
                    if (detailsList.isNotEmpty()) {
                        _uiState.value = DetailsUiState.Success(detailsList.first())
                    } else {
                        _uiState.value = DetailsUiState.Error("Não foi possível carregar o clima de $cityName")
                    }
                }
        }
    }
}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Success(val data: ForecastWithDetails) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}