package com.example.aplicativodeprevisodotempo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aplicativodeprevisodotempo.presentation.screens.details.DetailsViewModel
import com.example.aplicativodeprevisodotempo.presentation.screens.search.SearchViewModel
import com.example.aplicativodeprevisodotempo.presentation.screens.favorites.FavoritesScreen
import com.example.aplicativodeprevisodotempo.presentation.screens.details.WeatherDetailScreen
import com.example.aplicativodeprevisodotempo.presentation.screens.search.WeatherListScreen
import com.example.aplicativodeprevisodotempo.presentation.screens.home.HomeWeatherScreen
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Search : Screen("search")
    object Details : Screen("details/{cityName}") {
        fun createRoute(cityName: String) = "details/$cityName"
    }
}

@Composable
fun WeatherNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeWeatherScreen(
                viewModel = koinViewModel(),
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                onNavigateToDetails = { cityName ->
                    navController.navigate(Screen.Details.createRoute(cityName))
                }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = koinViewModel(),
                onCityClick = { cityName ->
                    navController.navigate(Screen.Details.createRoute(cityName))
                }
            )
        }

        composable(Screen.Search.route) {
            val searchViewModel: SearchViewModel = koinViewModel()

            WeatherListScreen(
                viewModel = searchViewModel,
                onCityClick = { cityName ->
                    navController.navigate(Screen.Details.createRoute(cityName))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("cityName") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val detailsViewModel: DetailsViewModel = koinViewModel()

            LaunchedEffect(cityName) {
                detailsViewModel.loadCityDetails(cityName)
            }

            WeatherDetailScreen(
                viewModel = detailsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}