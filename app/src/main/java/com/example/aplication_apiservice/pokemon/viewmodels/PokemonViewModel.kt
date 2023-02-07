package com.example.aplication_apiservice.pokemon.viewmodels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplication_apiservice.pokemon.PokemonResponse
import com.example.aplication_apiservice.pokemon.repository.PokemonRepository
import com.example.aplication_apiservice.pokemon.service.PaySingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class PokemonViewModel: ViewModel() {

    private var nextPage = ""
    private var previousPage = ""
    private var page = 1
    private lateinit var repository : PokemonRepository
    private val disposable = CompositeDisposable()
    private val action = PaySingleLiveEvent<PokemonActions>()
    fun getActionLiveData() = action as LiveData<PokemonActions>

    private fun quertyPokemon(query:String="https://pokeapi.co/api/v2/pokemon/"){
        repository = PokemonRepository(query)
        disposable.add(
            repository.getListPokemon()
                .subscribe({
                           upDatePage(it)
                           nextPage = it.next
                           previousPage = it.previous ?: ""
                },{
                    throw Exception(it.toString())

                })

        )

    }

    fun inicialservice(){
        if (page==1 && nextPage == ""){
            quertyPokemon()
        }
    }

    fun btNext(){
        page++
        quertyPokemon(nextPage)
    }

    fun btPrevious(){
        if(page>1){
            page--
            quertyPokemon(nextPage)
        }
    }

    private fun upDatePage(response: PokemonResponse) {
        action.value = PokemonActions.OnShowPokemonesOperation(response.pokemones)
        action.value = PokemonActions.OnShowPageOperation("Pagina $page")
    }


    override fun onCleared() {
        disposable.clear()
    }



}