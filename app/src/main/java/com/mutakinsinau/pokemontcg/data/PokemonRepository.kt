package com.mutakinsinau.pokemontcg.data

import com.mutakinsinau.pokemontcg.data.network.PokemonService
import com.mutakinsinau.pokemontcg.data.network.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class PokemonRepository(private val pokemonService: PokemonService) {

    fun getPokemon(
        query: String? = null,
        page: Int? = null,
        pageSize: Int? = null
    ) = flow {
        try {
            emit(ApiResult.Loading(emptyList()))
            val result = pokemonService.searchCards(query, page, pageSize)
            emit(ApiResult.Success(result.data))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message ?: "Unknown Error"))
        }

    }.flowOn(Dispatchers.IO)


    suspend fun getDetailPokemon(
        id: String
    ) = flow {
        try {
            emit(ApiResult.Loading())
            val result = pokemonService.getDetailCard(id)
            emit(ApiResult.Success(result.data))
        } catch (e: HttpException) {
            emit(ApiResult.Error(e.message()))
        }
    }.flowOn(Dispatchers.IO)


}