package com.example.aplicativodeprevisodotempo.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.aplicativodeprevisodotempo.core.common.Constants
import com.example.aplicativodeprevisodotempo.data.local.relations.CityWithForecasts
import com.example.aplicativodeprevisodotempo.data.utils.CityLoader
import com.example.aplicativodeprevisodotempo.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedState = MutableStateFlow("Pernambuco")
    val selectedState = _selectedState.asStateFlow()

    private val _selectedRegion = MutableStateFlow("Nordeste")
    val selectedRegion = _selectedRegion.asStateFlow()

    init {
        loadCitiesForCurrentState()
    }

    val statesInRegion: StateFlow<List<String>> = _selectedRegion
        .flatMapLatest { region ->
            flowOf(CityLoader.getStatesForRegion(region))
        }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    val pagedCities: Flow<PagingData<CityWithForecasts>> = combine(
        _selectedState,
        _searchQuery
    ) { state, query -> state to query }
        .flatMapLatest { (state, query) ->
            Pager(
                config = PagingConfig(pageSize = 30, prefetchDistance = 10),
                pagingSourceFactory = { repository.getCitiesPagingSource(state, query) }
            ).flow
        }.cachedIn(viewModelScope)

    fun onSearchQueryChanged(newQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery.value = newQuery
        }
    }
    fun onCityClick(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.syncWeatherByCityName(cityName, Constants.API_KEY)
        }
    }

    fun onRegionSelected(region: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedRegion.value = region
            val firstState = CityLoader.getStatesForRegion(region).firstOrNull()
            if (firstState != null) onStateSelected(firstState)
        }
    }

    fun onStateSelected(state: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedState.value = state
            loadCitiesForCurrentState()
        }
    }

    private fun loadCitiesForCurrentState() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _selectedState.value
            val region = _selectedRegion.value
            val citiesNames = CityLoader.getCitiesForState(region, state)
            repository.updateCityListByState(state, citiesNames)
        }
    }

    fun toggleFavorite(cityName: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateFavoriteStatus(cityName, isFavorite)
            }
        }
    }
}