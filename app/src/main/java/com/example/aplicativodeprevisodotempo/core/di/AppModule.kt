package com.example.aplicativodeprevisodotempo.core.di

import com.example.aplicativodeprevisodotempo.core.data.DataStoreManager
import com.example.aplicativodeprevisodotempo.core.data.repository.WeatherRepository
import com.example.aplicativodeprevisodotempo.core.data.repository.WeatherRepositoryImpl
import com.example.aplicativodeprevisodotempo.core.data.utils.Constants
import com.example.aplicativodeprevisodotempo.core.local.dao.CityDao
import com.example.aplicativodeprevisodotempo.core.local.dao.WeatherDao
import com.example.aplicativodeprevisodotempo.core.local.db.WeatherDatabase
import com.example.aplicativodeprevisodotempo.core.remote.api.WeatherService
import com.example.aplicativodeprevisodotempo.core.viewmodel.DetailsViewModel
import com.example.aplicativodeprevisodotempo.core.viewmodel.FavoritesViewModel
import com.example.aplicativodeprevisodotempo.core.viewmodel.HomeViewModel
import com.example.aplicativodeprevisodotempo.core.viewmodel.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
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


val databaseModule = module {
    single<WeatherDatabase> { WeatherDatabase.getDatabase(get()) }
    single<WeatherDao> { get<WeatherDatabase>().weatherDao() }
    single<CityDao> { get<WeatherDatabase>().cityDao() }
    single { DataStoreManager(get()) }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            api = get(),
            cityDao = get<CityDao>(),
            weatherDao = get<WeatherDao>(),
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
