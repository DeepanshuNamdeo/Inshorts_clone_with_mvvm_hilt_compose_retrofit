package com.test.inshortsclone.data.repository

import com.test.inshortsclone.data.entities.FeedResponse
import com.test.inshortsclone.data.remote.FeedRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class FeedRepositoryImpl
@Inject
constructor(private val feedRemoteDataSource: FeedRemoteDataSource) : FeedRepository {

    override suspend fun getListOfFeedForCategory(category: String): FeedResponse =
        feedRemoteDataSource.getListOfFeedForCategory(category)
}