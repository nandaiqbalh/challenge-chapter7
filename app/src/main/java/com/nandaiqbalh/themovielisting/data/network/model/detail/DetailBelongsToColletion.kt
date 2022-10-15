package com.nandaiqbalh.themovielisting.data.network.model.detail

import com.google.gson.annotations.SerializedName

data class DetailBelongsToColletion(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null
)