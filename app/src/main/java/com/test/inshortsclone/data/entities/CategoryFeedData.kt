package com.test.inshortsclone.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CategoryFeedData(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("readMoreUrl")
    val readMoreUrl: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)