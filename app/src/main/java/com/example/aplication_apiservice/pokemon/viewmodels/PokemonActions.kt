package com.example.aplication_apiservice.pokemon.viewmodels

import com.example.aplication_apiservice.pokemon.Pokemon

sealed class PokemonActions {
    data class OnShowPokemonesOperation(val result: MutableList<Pokemon>): PokemonActions()
    data class OnShowPaginaOperation(val result: Int): PokemonActions()
}
