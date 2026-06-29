package com.example.movieapp.data.di

import com.example.movieapp.data.di.dataSourceModule.dataSourceModule
import com.example.movieapp.data.di.datamodule.dataModule
import com.example.movieapp.data.di.localmodule.localDataModule
import com.example.movieapp.data.di.remotemodule.remoteModule
import com.example.movieapp.data.di.usecasemodule.useCaseModule
import com.example.movieapp.network.networkmodule.networkModule
import org.koin.dsl.module

val coreDataModule = module {
    includes(
        dataModule,
        remoteModule,
        useCaseModule,
        networkModule,
        localDataModule,
        dataSourceModule
    )
}