package com.mutakinsinau.pokemontcg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutakinsinau.pokemontcg.data.PokemonRepository
import com.mutakinsinau.pokemontcg.data.network.dto.Pokemon
import com.mutakinsinau.pokemontcg.data.network.util.ApiResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _listPokemon = MutableLiveData<List<Pokemon>>()
    val listPokemon: LiveData<List<Pokemon>> get() = _listPokemon

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPokemon(
        query: String? = null,
        page: Int? = null,
        pageSize: Int? = null
    ) {

        viewModelScope.launch {
            repository.getPokemon(query, page, pageSize).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        _isLoading.value = false
                        _listPokemon.value = result.data ?: emptyList()
                    }
                    is ApiResult.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error ?: "Unknown Error"
                    }
                    is ApiResult.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }

    }
}