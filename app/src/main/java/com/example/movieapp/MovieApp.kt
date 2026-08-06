package com.example.movieapp

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import com.example.movieapp.data.di.coreDataModule
import com.example.movieapp.home.di.homeViewModelModule
import com.example.movieapp.favourite.di.favoriteViewModelModule
import com.example.movieapp.moviedetail.di.movieDetailViewModelModule
import com.example.movieapp.splash.di.splashViewModelModule

class MovieApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(
                coreDataModule,
                homeViewModelModule,
                favoriteViewModelModule,
                movieDetailViewModelModule,
                splashViewModelModule
            )
        }
    }
}