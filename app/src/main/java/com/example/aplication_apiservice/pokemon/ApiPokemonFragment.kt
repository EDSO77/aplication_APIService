package com.example.aplication_apiservice.pokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplication_apiservice.databinding.FragmentApiPokemonBinding
import com.example.aplication_apiservice.pokemon.service.PokemonAPIService
import com.example.aplication_apiservice.pokemon.viewmodels.PokemonActions
import com.example.aplication_apiservice.pokemon.viewmodels.PokemonViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiPokemonFragment : Fragment() {

    private lateinit var pokemonViewModel : PokemonViewModel

    private val binding: FragmentApiPokemonBinding by lazy{
        FragmentApiPokemonBinding.inflate(layoutInflater)
    }
    private lateinit var adapter : PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        bindActionViewModel()
        pokemonViewModel.inicialservice()
        initReciclerView()

        binding.btNext.setOnClickListener {
           pokemonViewModel.btNext()
        }
        binding.btPrevious.setOnClickListener {
           pokemonViewModel.btPrevious()
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun bindActionViewModel() {
        pokemonViewModel.getActionLiveData().observe(viewLifecycleOwner, this::handleAction)
    }


    private fun handleAction(actions: PokemonActions) {
        when (actions) {
            is PokemonActions.OnShowPokemonesOperation -> buildAdapter(actions.result)

            is PokemonActions.OnShowPageOperation -> binding.tvPagina.text = actions.result

        }
    }

        fun buildAdapter(pokemones: List<Pokemon>) {
        adapter = PokemonAdapter(pokemones)

    }
        fun initReciclerView() {
        binding.rvPokemones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPokemones.adapter = adapter

    }

}
