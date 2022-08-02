package com.test.inshortsclone.data.remote

import com.test.inshortsclone.data.entities.FeedResponse
import retrofit2.Response
import javax.inject.Inject

class FeedRemoteDataSourceImpl
@Inject constructor(private val apiService: ApiService) : FeedRemoteDataSource {
    override suspend fun getListOfFeedForCategory(category: String): FeedResponse =
        apiService.getListOfFeedForCategory(category)
}
