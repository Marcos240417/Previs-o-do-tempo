package com.example.aplicativodeprevisodotempo.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.aplicativodeprevisodotempo.presentation.components.effect.getDynamicBackgroundColor
import com.example.aplicativodeprevisodotempo.ui.theme.ElectricCyan
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(
    viewModel: DetailsViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val currentTemp = (state as? DetailsUiState.Success)?.data?.info?.temp ?: 25f
    val dynamicBg = getDynamicBackgroundColor(currentTemp)

    Box(modifier = Modifier.fillMaxSize().background(dynamicBg)) {
        when (val currentState = state) {
            is DetailsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = ElectricCyan)
                }
            }

            is DetailsUiState.Success -> {
                val data = currentState.data
                val current = data.info

                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = current.parentCityName,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = onBack) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color.White)
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        val iconRes = Constants.getWeatherIcon(current.icon)

                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = current.description,
                            modifier = Modifier
                                .size(180.dp)
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "${current.temp.toInt()}°",
                            fontSize = 100.sp,
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

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            DetailColumn("Mínima", "${current.tempMin.toInt()}°")
                            DetailColumn("Máxima", "${current.tempMax.toInt()}°")
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            DetailColumn("Umidade", "${current.humidity}%")
                            DetailColumn("Pressão", "${current.pressure.toInt()} hPa")
                        }
                    }
                }
            }

            is DetailsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = currentState.message, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DetailColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}