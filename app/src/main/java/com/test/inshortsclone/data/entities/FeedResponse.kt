package com.test.inshortsclone.data.entities


import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("data")
    val `data`: List<CatagoryFeedData>,
    @SerializedName("success")
    val success: Boolean
)