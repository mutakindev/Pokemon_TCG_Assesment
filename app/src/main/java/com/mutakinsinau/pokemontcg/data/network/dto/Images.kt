package com.mutakinsinau.pokemontcg.data.network.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(

	@field:SerializedName("small")
	val small: String,

	@field:SerializedName("large")
	val large: String
) : Parcelable
