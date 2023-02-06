package com.example.aplication_apiservice.pokemon.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplication_apiservice.pokemon.service.PokemonAPIService
import com.example.aplication_apiservice.pokemon.PokemonResponse
import com.example.aplication_apiservice.pokemon.repository.PokemonRepository
import com.example.aplication_apiservice.pokemon.service.PaySingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokemonViewModel: ViewModel() {


    private val repository = PokemonRepository()

    private val disposable = CompositeDisposable()

    private val action = PaySingleLiveEvent<PokemonActions>()
    fun getActionLiveData() = action as LiveData<PokemonActions>

    private fun quertyPokemonRx(query:String="https://pokeapi.co/api/v2/pokemon/"){

        disposable.add(
            repository.getListPokemon()
                //*** debo investigar como ver un Log en el observer para este caso
                .subscribe({

                },{

                })

        )

    }

    private fun getRetrofit(url:String): Retrofit {
        Log.d("TAG2", "fun getRetrofit")
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun onCleared() {
        disposable.clear()
    }



}