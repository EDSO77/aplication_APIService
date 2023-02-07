package com.example.aplication_apiservice.pokemon.viewmodels

import com.example.aplication_apiservice.pokemon.Pokemon

sealed class PokemonActions {
    data class OnShowPokemonesOperation(val result: List<Pokemon>): PokemonActions()
    data class OnShowPageOperation(val result: String): PokemonActions()
}
