package com.example.aplication_apiservice.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.example.aplication_apiservice.R


class PokemonAdapter(val pokemones: List<Pokemon>): RecyclerView.Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        Log.d("TAG2", "onCreateViewHolder ${Thread.currentThread().getName()}")
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon,parent,false))
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item=pokemones[position]
        holder.render(item)
        Log.d("TAG2", "onBindViewHolder ${Thread.currentThread().getName()}")
    }

    override fun getItemCount(): Int = pokemones.size
}