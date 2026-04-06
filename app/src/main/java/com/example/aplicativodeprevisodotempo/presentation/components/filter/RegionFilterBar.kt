package com.example.aplicativodeprevisodotempo.presentation.components.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // IMPORTANTE: Sem o .items, o 'region' vira Int
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aplicativodeprevisodotempo.core.common.CityLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionFilterBar(
    selectedRegion: String,
    onRegionSelected: (String) -> Unit
) {
    val regionsList = CityLoader.getAvailableRegions()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(regionsList) { region ->
            FilterChip(
                selected = selectedRegion == region,

                onClick = { onRegionSelected(region) },
                label = {
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