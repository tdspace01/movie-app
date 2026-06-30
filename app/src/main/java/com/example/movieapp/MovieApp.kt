package com.example.movieapp

import android.app.Application
import com.example.movieapp.data.di.coreDataModule
import com.example.movieapp.favourite.favouritescreen.di.favoriteViewModelModule
import com.example.movieapp.home.home.di.homeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                coreDataModule,
                homeViewModelModule,
                favoriteViewModelModule
            )
        }
    }
}