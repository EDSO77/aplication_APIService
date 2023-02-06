package com.example.aplication_apiservice.pokemon.repository

import com.example.aplication_apiservice.pokemon.PokemonResponse
import com.example.aplication_apiservice.pokemon.service.Service
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonRepository {

    private val pokemonService = Service()

     fun getListPokemon () : Single<PokemonResponse> {
        return pokemonService.api.getPokemonesRx("") .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}