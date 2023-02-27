package com.mutakinsinau.pokemontcg.data.network.dto

import com.google.gson.annotations.SerializedName

data class SinglePokemonResponse(
	@field:SerializedName("data")
	val data: Pokemon,
)
