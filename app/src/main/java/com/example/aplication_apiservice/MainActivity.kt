package com.example.aplication_apiservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.aplication_apiservice.databinding.ActivityMainBinding
import com.example.aplication_apiservice.pokemon.ApiPokemonFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fragments_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if (item.itemId==R.id.itemDog){
           supportFragmentManager.beginTransaction().apply {
               replace(R.id.mainNavHost,ApiDogFragment())
               commit()
           }
       }
       if (item.itemId==R.id.itemPokemon){
           supportFragmentManager.beginTransaction().apply {
               replace(R.id.mainNavHost, ApiPokemonFragment())
               commit()
           }
       }
       if (item.itemId==R.id.itemNasa){
           supportFragmentManager.beginTransaction().apply {
               replace(R.id.mainNavHost,ApiNasaFragment())
               commit()
           }
       }


        return super.onOptionsItemSelected(item)
    }
}