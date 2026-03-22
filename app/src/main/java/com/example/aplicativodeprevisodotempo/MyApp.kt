package com.example.aplicativodeprevisodotempo

import android.app.Application
import com.example.aplicativodeprevisodotempo.core.di.dataModule
import com.example.aplicativodeprevisodotempo.core.di.networkModule
import com.example.aplicativodeprevisodotempo.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    dataModule,
                    viewModelModule
                )
            )
        }
    }
}



