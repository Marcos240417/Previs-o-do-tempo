package com.example.aplicativodeprevisodotempo.core.di

import com.example.aplicativodeprevisodotempo.data.storage.DataStoreManager
import com.example.aplicativodeprevisodotempo.domain.repository.WeatherRepository
import com.example.aplicativodeprevisodotempo.data.repository.WeatherRepositoryImpl
import com.example.aplicativodeprevisodotempo.core.common.Constants
import com.example.aplicativodeprevisodotempo.data.local.db.WeatherDatabase
import com.example.aplicativodeprevisodotempo.data.remote.api.WeatherService
import com.example.aplicativodeprevisodotempo.presentation.screens.details.DetailsViewModel
import com.example.aplicativodeprevisodotempo.presentation.screens.favorites.FavoritesViewModel
import com.example.aplicativodeprevisodotempo.presentation.screens.home.HomeViewModel
import com.example.aplicativodeprevisodotempo.presentation.screens.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single<WeatherService> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}


val dataModule = module {
    single { WeatherDatabase.getDatabase(get()) }
    single { get<WeatherDatabase>().weatherDao() }
    single { get<WeatherDatabase>().cityDao() }

    single { DataStoreManager(get()) }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            api = get(),
            cityDao = get(),
            weatherDao = get(),
            dataStore = get()
        )
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}

