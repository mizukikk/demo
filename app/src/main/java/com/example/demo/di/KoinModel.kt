package com.example.demo.di

import com.example.demo.BuildConfig
import com.example.demo.api.NetworkService
import com.example.demo.repository.GithubRepository
import com.example.demo.repository.remote.GithubRemoteDataSource
import com.example.demo.repository.remote.IGithubRemoteDataSource
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val koinModule = module {
    single {
        if (BuildConfig.DEBUG)
            NetworkService(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        else
            NetworkService(null)
    }

    single {
        val networkService = get<NetworkService>()
        GithubRemoteDataSource(networkService.githubService) as IGithubRemoteDataSource
    }

    single {
        GithubRepository(get())
    }
}