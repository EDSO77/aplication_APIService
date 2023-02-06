package com.example.aplication_apiservice.pokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private lateinit var pokemonViewModel: PokemonViewModel

    private val binding: FragmentApiPokemonBinding by lazy{
        FragmentApiPokemonBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: PokemonAdapter
    var pokemones = mutableListOf<Pokemon>()
    var nextPage = ""
    var previousPage = ""
    var pagina = 1
    //*** Se crea una instancia del CompositeDisposable, para agregar las relaciones Observador Observable
    var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindActionViewModel()
        initReciclerView()


        upToDateCurrentPage()
        quertyPokemonRx()

        binding.btNext.setOnClickListener {
            quertyPokemonRx(nextPage)
            pagina++
            upToDateCurrentPage()
        }

        binding.btPrevious.setOnClickListener {
            if (pagina>1) {
                quertyPokemonRx(previousPage)
                pagina--
                upToDateCurrentPage()
            }else{
                Toast.makeText(requireContext(), "Has llegado a la primer pagina", Toast.LENGTH_SHORT).show()
            }

        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindActionViewModel() {
        pokemonViewModel.getActionLiveData().observe(viewLifecycleOwner, this::handleAction)
    }


    private fun handleAction(actions: PokemonActions) {
        when (actions) {
            is PokemonActions.OnShowPokemonesOperation -> buildAdapter(actions.result)
            is PokemonActions.OnShowPaginaOperation -> binding.tvPagina.text = actions.result.toString()

        }
    }

    private fun upToDateCurrentPage() {
        var page = "Pagina $pagina"
        binding.tvPagina.text = "hola"

        Log.d("TAG2", "upToDateCurrentPage ${pagina}")
    }
        fun buildAdapter(pokemones: List<Pokemon>) {
        adapter = PokemonAdapter(pokemones)

    }
        fun initReciclerView() {
        binding.rvPokemones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPokemones.adapter = adapter

    }




    private fun quertyPokemonRx(query:String="https://pokeapi.co/api/v2/pokemon/"){

        compositeDisposable.add(
            getRetrofit(query)
                .create(PokemonAPIService::class.java)
                .getPokemonesRx("")
                //*** debo investigar como ver un Log en el observer para este caso
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<PokemonResponse> {
                    override fun accept(t: PokemonResponse?) {
                        Log.d("TAG2", "on accepted ${Thread.currentThread().getName()}")
                        if (t!=null) {
                            Log.d("TAG2", "on if ${t.pokemones.size}")
                            val images = t.pokemones ?: emptyList()
                            pokemones.clear()
                            pokemones.addAll(images)
                            nextPage = t.next ?: ""
                            previousPage = t.previous ?: ""
                            Log.d("TAG2", "on if 2 ${t.pokemones.size}")
                            //*** Preguntar a ivan a que se refiere la sugerencia
                            adapter.notifyDataSetChanged()

                            Log.d("TAG2", "notifyDataSetChanged()")
                            Toast.makeText(requireContext(), "Actualizado con Rx", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

        )

    }

  /*  private fun quertyPokemon(query:String="https://pokeapi.co/api/v2/pokemon/") {



            val call = getRetrofit(query).create(PokemonAPIService::class.java).getPokemones("")
            val info = call.body()

                if (call.isSuccessful) {
                    //hacemos las igualaciones a las lista y eso
                    val images = info?.pokemones ?: emptyList()
                    pokemones.clear()
                    pokemones.addAll(images)
                    nextPage = info?.next ?: ""
                    previousPage = info?.previous ?: ""
                    adapter.notifyDataSetChanged()
                }else {
                    Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()}



    }*/

    private fun getRetrofit(url:String): Retrofit {
        Log.d("TAG2", "fun getRetrofit")
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}
