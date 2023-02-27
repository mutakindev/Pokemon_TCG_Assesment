package com.mutakinsinau.pokemontcg.data.network.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(

	@field:SerializedName("images")
	val images: Images,

	@field:SerializedName("types")
	val types: List<String>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
) : Parcelable
