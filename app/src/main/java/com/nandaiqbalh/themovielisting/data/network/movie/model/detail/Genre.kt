package com.nandaiqbalh.themovielisting.data.network.movie.model.detail

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)