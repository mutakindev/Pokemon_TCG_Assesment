package com.mutakinsinau.pokemontcg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mutakinsinau.pokemontcg.data.PokemonRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: PokemonRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }

        throw ClassNotFoundException("Unknown Class Name")
    }
}