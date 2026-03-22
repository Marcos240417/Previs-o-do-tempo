package com.example.aplicativodeprevisodotempo.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicativodeprevisodotempo.core.common.Constants
import com.example.aplicativodeprevisodotempo.presentation.screens.home.HomeViewModel
import com.example.aplicativodeprevisodotempo.presentation.components.effect.getDynamicBackgroundColor
import com.example.aplicativodeprevisodotempo.ui.theme.ElectricCyan
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeWeatherScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToDetails: (String) -> Unit
) {
    val weatherData by viewModel.currentLocationWeather.collectAsStateWithLifecycle()
    val cityName by viewModel.lastCityName.collectAsStateWithLifecycle()
    val allCities by viewModel.allCitiesWeather.collectAsStateWithLifecycle()

    val dynamicBg = getDynamicBackgroundColor(weatherData?.forecasts?.firstOrNull()?.temp ?: 25f)

    LaunchedEffect(Unit) {
        viewModel.refreshLocationWeather()
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.refreshLocationWeather() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Atualizar",
                        tint = Color.White
                    )
                }

                IconButton(onClick = onNavigateToFavorites) {
                    BadgedBox(badge = {
                        if (allCities.isNotEmpty()) Badge { Text(allCities.size.toString()) }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = Color.White
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToSearch,
                containerColor = ElectricCyan,
                contentColor = Color.Black,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .height(64.dp)
                    .fillMaxWidth(0.8f),
                icon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(28.dp)) },
                text = {
                    Text(
                        text = "EXPLORAR CIDADES",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(dynamicBg)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .clickable { cityName?.let { onNavigateToDetails(it) } },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = cityName ?: "Localizando...",
                        fontSize = 24.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium
                    )

                    weatherData?.forecasts?.firstOrNull()?.let { current ->
                        val iconRes = Constants.getWeatherIcon(current.icon)

                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = current.description,
                            modifier = Modifier
                                .size(180.dp)
                                .padding(vertical = 8.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "${current.temp.toInt()}°",
                            fontSize = 120.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        Text(
                            text = current.description.uppercase(),
                            fontSize = 18.sp,
                            letterSpacing = 2.sp,
                            color = ElectricCyan,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            HomeDetailItem("Umidade", "${current.humidity}%")
                            HomeDetailItem("Pressão", "${current.pressure.toInt()} hPa")
                        }
                    } ?: run {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = ElectricCyan, strokeWidth = 4.dp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Sincronizando clima...", color = Color.White.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}