package com.example.aplicativodeprevisodotempo.presentation.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicativodeprevisodotempo.core.common.Constants // Certifique-se de ter sua API_KEY aqui
import com.example.aplicativodeprevisodotempo.presentation.components.cards.WeatherCardModern
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    onCityClick: (String) -> Unit,
) {
    val favoriteCities by viewModel.favoriteCities.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cidades Favoritas", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {
                        viewModel.refreshFavorites(Constants.API_KEY)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Atualizar Clima",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (favoriteCities.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhuma cidade favoritada em Pernambuco.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(
                    items = favoriteCities,
                    key = { it.city.cityName }
                ) { item ->
                    WeatherCardModern(
                        cityWithForecast = item,
                        onClick = { onCityClick(item.city.cityName) },
                        onFavoriteClick = {
                            viewModel.toggleFavorite(item.city.cityName, !item.city.isFavorite)
                        }
                    )
                }
            }
        }
    }
}