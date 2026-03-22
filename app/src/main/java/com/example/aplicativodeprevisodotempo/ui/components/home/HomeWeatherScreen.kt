package com.example.aplicativodeprevisodotempo.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import com.example.aplicativodeprevisodotempo.core.data.utils.Constants
import com.example.aplicativodeprevisodotempo.core.viewmodel.HomeViewModel
import com.example.aplicativodeprevisodotempo.ui.components.effect.getDynamicBackgroundColor
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

    val dynamicBg = getDynamicBackgroundColor()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 16.dp, vertical = 8.dp)) {
                IconButton(
                    onClick = onNavigateToFavorites,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favoritos",
                        tint = Color.White
                    )
                }
            }
        },
        floatingActionButton = {
            // BOTÃO AMPLIADO E COM NOVO TEXTO
            ExtendedFloatingActionButton(
                onClick = onNavigateToSearch,
                containerColor = ElectricCyan,
                contentColor = Color.Black,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .height(64.dp), // Aumentando a altura do botão
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
        floatingActionButtonPosition = FabPosition.Center // Centraliza o botão grande na parte inferior
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
                // --- CONTEÚDO CENTRAL ---
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
                                .size(160.dp)
                                .padding(vertical = 12.dp),
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

                        Spacer(modifier = Modifier.height(40.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            HomeDetailItem("Umidade", "${current.humidity}%")
                            HomeDetailItem("Pressão", "${current.pressure.toInt()} hPa")
                        }
                    } ?: run {
                        CircularProgressIndicator(color = ElectricCyan)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}