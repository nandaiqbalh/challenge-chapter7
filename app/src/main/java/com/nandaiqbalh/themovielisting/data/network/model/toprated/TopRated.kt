package com.nandaiqbalh.themovielisting.data.network.model.toprated

import com.google.gson.annotations.SerializedName
import com.nandaiqbalh.themovielisting.data.network.model.popular.PopularItem

data class TopRated(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: ArrayList<TopRatedItem>? = null
)