package com.example.aplication_apiservice.pokemon.service

import com.example.aplication_apiservice.pokemon.PokemonResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonAPIService {
    //Sin RxJava
    @GET
    fun getPokemones(@Url url:String):Response<PokemonResponse>

    //Con RxJava
    @GET
    fun getPokemonesRx(@Url url:String):Single<PokemonResponse>


}