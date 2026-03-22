package com.example.aplicativodeprevisodotempo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.aplicativodeprevisodotempo.core.viewmodel.SearchViewModel
import com.example.aplicativodeprevisodotempo.ui.components.effect.WeatherCardPlaceholder
import com.example.aplicativodeprevisodotempo.ui.components.effect.getDynamicBackgroundColor
import com.example.aplicativodeprevisodotempo.ui.components.filterbar.RegionFilterBar
import com.example.aplicativodeprevisodotempo.ui.theme.ElectricCyan
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherListScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onCityClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedRegion by viewModel.selectedRegion.collectAsStateWithLifecycle()
    val selectedState by viewModel.selectedState.collectAsStateWithLifecycle()
    val statesInRegion by viewModel.statesInRegion.collectAsStateWithLifecycle()

    val pagingItems = viewModel.pagedCities.collectAsLazyPagingItems()

    val firstItemTemp = pagingItems.itemSnapshotList.firstOrNull()?.forecasts?.firstOrNull()?.temp ?: 25f
    val dynamicBg = getDynamicBackgroundColor(firstItemTemp)

    Box(modifier = Modifier.fillMaxSize().background(dynamicBg)) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Column(modifier = Modifier.statusBarsPadding().padding(top = 16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.White)
                        }
                        Text(
                            text = if (selectedState.isEmpty()) "Explorar Brasil" else "Cidades em $selectedState",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SearchCityBar(
                        query = searchQuery,
                        onQueryChange = { novoTexto -> viewModel.onSearchQueryChanged(novoTexto) }
                    )
                    RegionFilterBar(
                        selectedRegion = selectedRegion,
                        onRegionSelected = { regiao -> viewModel.onRegionSelected(regiao) }
                    )

                    if (statesInRegion.isNotEmpty()) {
                        InternalStateFilterBar(
                            states = statesInRegion,
                            selectedState = selectedState,
                            onStateSelected = { estado -> viewModel.onStateSelected(estado) }
                        )
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (pagingItems.loadState.refresh is LoadState.Loading) {
                        items(6) { WeatherCardPlaceholder() }
                    }

                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.city.cityName }
                    ) { index ->
                        val cidadeData = pagingItems[index]
                        if (cidadeData != null) {
                            WeatherCardModern(
                                cityWithForecast = cidadeData,
                                onClick = { cityName ->
                                    viewModel.onCityClick(cityName)
                                    onCityClick(cityName)
                                },
                                onFavoriteClick = {
                                    viewModel.toggleFavorite(cidadeData.city.cityName, !cidadeData.city.isFavorite)
                                }
                            )
                        }
                    }

                    if (pagingItems.loadState.append is LoadState.Loading) {
                        item {
                            Box(Modifier.fillMaxWidth().padding(16.dp), Alignment.Center) {
                                CircularProgressIndicator(color = ElectricCyan, strokeWidth = 2.dp)
                            }
                        }
                    }
                }

                if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh is LoadState.NotLoading) {
                    Text(
                        text = "Nenhuma cidade encontrada.",
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InternalStateFilterBar(
    states: List<String>,
    selectedState: String,
    onStateSelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        items(states) { estado ->
            FilterChip(
                selected = selectedState == estado,
                onClick = { onStateSelected(estado) },
                label = { Text(estado) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricCyan,
                    selectedLabelColor = Color.Black,
                    labelColor = Color.White,
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                border = null
            )
        }
    }
}