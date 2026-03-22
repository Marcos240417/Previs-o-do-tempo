package com.example.aplicativodeprevisodotempo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativodeprevisodotempo.core.data.utils.Constants
import com.example.aplicativodeprevisodotempo.core.local.relations.CityWithForecasts

/**
 * Componente moderno para exibição de clima por cidade.
 * Resolve o requisito do desafio: "Fix broken weather icons".
 */
@Composable
fun WeatherCardModern(
    cityWithForecast: CityWithForecasts,
    onClick: (String) -> Unit,
    onFavoriteClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    // Dispara a animação de entrada
    LaunchedEffect(Unit) { visible = true }

    val city = cityWithForecast.city
    val forecast = cityWithForecast.forecasts.firstOrNull()

    // Converte temperatura para Double para lógica de cores dinâmica
    val tempValue = forecast?.temp?.toDouble() ?: 0.0

    // Gradiente dinâmico baseado na temperatura (Toque de brilho no app!)
    val cardGradient = when {
        tempValue > 28.0 -> Brush.linearGradient(listOf(Color(0xFFFF5F6D), Color(0xFFFFC371)))
        tempValue < 22.0 -> Brush.linearGradient(listOf(Color(0xFF4FACFE), Color(0xFF00F2FE)))
        else -> Brush.linearGradient(listOf(Color(0xFF436E67), Color(0xFF2C4D48)))
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(800)) +
                scaleIn(initialScale = 0.9f, animationSpec = tween(800))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(170.dp)
                .clickable { onClick(city.cityName) },
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(cardGradient)
                    .padding(20.dp)
            ) {

                // --- BOTÃO DE FAVORITO ---
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                ) {
                    Icon(
                        imageVector = if (city.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favoritar",
                        tint = if (city.isFavorite) Color(0xFFFF4D4D) else Color.White
                    )
                }

                // --- INFORMAÇÕES PRINCIPAIS ---
                Column(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "CLIMA ATUAL",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = city.cityName,
                        fontSize = 26.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        forecast?.let {
                            WeatherBadge("${it.temp.toInt()}°C")
                            WeatherBadge(it.description.uppercase())
                        }
                    }
                }

                // --- ÍCONE DO CLIMA DINÂMICO ---
                forecast?.let {
                    // Mapeia o código da API (ex: "01d") para o drawable local
                    val iconRes = Constants.getWeatherIcon(it.icon)

                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = it.description,
                        modifier = Modifier
                            .size(110.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = 12.dp, y = 12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

/**
 * Pequeno selo estilizado para temperatura e descrição.
 */
@Composable
fun WeatherBadge(label: String) {
    Surface(
        color = Color.White.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            0.5.dp,
            Color.White.copy(alpha = 0.3f)
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}