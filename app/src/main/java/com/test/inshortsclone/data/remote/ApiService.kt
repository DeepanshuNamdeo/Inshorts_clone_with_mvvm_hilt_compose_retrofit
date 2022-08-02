package com.test.inshortsclone.data.remote

import com.test.inshortsclone.data.entities.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("news")
    suspend fun getListOfFeedForCategory( @Query("category") category : String) : FeedResponse

}