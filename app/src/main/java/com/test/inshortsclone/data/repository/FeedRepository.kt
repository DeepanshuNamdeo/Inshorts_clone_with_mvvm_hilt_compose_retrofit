package com.test.inshortsclone.data.repository

import com.test.inshortsclone.data.entities.CategoryFeedData
import com.test.inshortsclone.data.entities.FeedResponse
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    suspend fun getListOfFeedForCategory(category: String): FeedResponse
    //Database op
    fun getListOfBookmark(): Flow<MutableList<CategoryFeedData>>
    suspend fun addBookmark(feed : CategoryFeedData)
    suspend fun deleteBookmark(feed : CategoryFeedData)
    fun isBookmarked(feedId: String) : Boolean
}