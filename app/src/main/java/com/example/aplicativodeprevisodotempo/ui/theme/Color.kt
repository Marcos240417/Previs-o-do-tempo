package com.example.aplicativodeprevisodotempo.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- CORES DE ACENTO (Botões e Destaques) ---
val HotPink = Color(0xFFE91E63)    // O seu rosa choque atual
val ElectricCyan = Color(0xFF00E5FF) // Ciano para info de vento/umidade
val SoftWhite = Color(0xFFF5F5F5)

// --- PALETA DIA (Antes das 18h) ---
val DaySkyTop = Color(0xFF4CA1AF)    // Azul Turquesa
val DaySkyBottom = Color(0xFF2C3E50) // Azul Oceano
val DayCard = Color(0x33FFFFFF)      // Branco com 20% de transparência (Glassmorphism)

// --- PALETA NOITE (Depois das 18h) ---
val NightSkyTop = Color(0xFF08101F)    // Azul Espacial Profundo
val NightSkyBottom = Color(0xFF1B3D7B) // Azul Royal Escuro
val NightCard = Color(0x33000000)      // Preto com 20% de transparência

// --- GRADIENTES PRONTOS PARA USO ---
val DayGradient = Brush.verticalGradient(listOf(DaySkyTop, DaySkyBottom))
val NightGradient = Brush.verticalGradient(listOf(NightSkyTop, NightSkyBottom))

// --- VARIAÇÕES POR ESTADO DE TEMPO (Opcional para dar "tchan") ---
val RainGradient = Brush.verticalGradient(listOf(Color(0xFF4B6CB7), Color(0xFF182848)))
val SunGradient = Brush.verticalGradient(listOf(Color(0xFFF2994A), Color(0xFFF2C94C))) // Para cidades ensolaradas