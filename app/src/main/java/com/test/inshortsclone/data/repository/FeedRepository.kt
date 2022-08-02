package com.test.inshortsclone.data.repository

import com.test.inshortsclone.data.entities.FeedResponse
import retrofit2.Response

interface FeedRepository {
    suspend fun getListOfFeedForCategory(category: String): FeedResponse
}