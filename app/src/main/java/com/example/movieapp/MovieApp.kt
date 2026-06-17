package com.example.movieapp

import android.app.Application
import com.example.movieapp.data.di.dataModule
import com.example.movieapp.network.network_module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                dataModule,
                networkModule
            )
        }
    }
}