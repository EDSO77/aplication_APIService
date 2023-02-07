package com.example.aplication_apiservice.pokemon.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Service(baseUrl: String) {

    private val okhttp = OkHttpClient.Builder()
        .connectTimeout(120,TimeUnit.SECONDS)
        .readTimeout(240,TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(okhttp)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(PokemonAPIService::class.java)


}