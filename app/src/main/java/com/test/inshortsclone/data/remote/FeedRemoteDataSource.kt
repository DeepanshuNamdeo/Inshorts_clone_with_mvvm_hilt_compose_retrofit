package com.test.inshortsclone.data.remote

import com.test.inshortsclone.data.entities.FeedResponse
import retrofit2.Response

interface FeedRemoteDataSource {
    suspend fun getListOfFeedForCategory( category : String) : FeedResponse
}