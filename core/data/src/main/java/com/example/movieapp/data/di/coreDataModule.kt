package com.example.movieapp.data.di

import com.example.movieapp.network.networkmodule.networkModule
import org.koin.dsl.module

val coreDataModule = module {
    includes(
        networkModule,
        remoteModule,
        dataModule,
        useCaseModule
    )
}