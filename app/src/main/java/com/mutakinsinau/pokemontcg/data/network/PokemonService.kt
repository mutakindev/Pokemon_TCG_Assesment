package com.mutakinsinau.pokemontcg.data.network

import com.mutakinsinau.pokemontcg.data.network.dto.PokemonResponse
import com.mutakinsinau.pokemontcg.data.network.dto.SinglePokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("/v2/cards")
    suspend fun searchCards(
        @Query("q") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
    ): PokemonResponse

    @GET("/v2/cards/{id}")
    suspend fun getDetailCard(
        @Path("id") id: String
    ): SinglePokemonResponse
}
