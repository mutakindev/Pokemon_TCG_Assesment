package com.mutakinsinau.pokemontcg.data.network.dto

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

	@field:SerializedName("data")
	val data: List<Pokemon>,

	@field:SerializedName("count")
	val count: Int?,

	@field:SerializedName("pageSize")
	val pageSize: Int?,

	@field:SerializedName("page")
	val page: Int?,

	@field:SerializedName("totalCount")
	val totalCount: Int?
)
