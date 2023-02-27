package com.mutakinsinau.pokemontcg.view

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.mutakinsinau.pokemontcg.data.PokemonRepository
import com.mutakinsinau.pokemontcg.data.network.ApiConfig
import com.mutakinsinau.pokemontcg.databinding.ActivityMainBinding
import com.mutakinsinau.pokemontcg.viewmodel.MainViewModel
import com.mutakinsinau.pokemontcg.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels(
        factoryProducer = { ViewModelFactory(PokemonRepository(ApiConfig.getApiService())) }
    )

    private lateinit var binding: ActivityMainBinding
    private val pokemonAdapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        binding.rvPokemon.setHasFixedSize(false)
        binding.rvPokemon.adapter = pokemonAdapter.apply {
            submitList(emptyList())
        }

        binding.searchbar.clearFocus()
        binding.searchbar.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                pokemonAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonAdapter.filter.filter(newText)
                return true
            }

        })

        mainViewModel.getPokemon(pageSize = 20)
        mainViewModel.errorMessage.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
        mainViewModel.isLoading.observe(this) { value ->
            binding.progressBar.isVisible = value
        }

        mainViewModel.listPokemon.observe(this) {
            pokemonAdapter.setData(it)
        }

    }
}