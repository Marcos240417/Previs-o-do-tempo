package com.example.aplicativodeprevisodotempo.ui.components.filterbar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // IMPORTANTE: Sem o .items, o 'region' vira Int
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aplicativodeprevisodotempo.core.data.utils.CityLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionFilterBar(
    selectedRegion: String, // Agora este parâmetro será usado!
    onRegionSelected: (String) -> Unit
) {
    val regionsList = CityLoader.getAvailableRegions()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // CORREÇÃO: Remova o 'count =' e passe a lista diretamente
        items(regionsList) { region ->
            FilterChip(
                // 1. AQUI O PARÂMETRO É USADO: Resolve o aviso "never used"
                selected = selectedRegion == region,

                onClick = { onRegionSelected(region) },
                label = {
                    // 2. AQUI O 'region' é String: Resolve o erro de "Int mismatch"
                    Text(text = region, color = Color.White)
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color.Cyan.copy(alpha = 0.4f),
                    containerColor = Color.White.copy(alpha = 0.1f)
                )
            )
        }
    }
}