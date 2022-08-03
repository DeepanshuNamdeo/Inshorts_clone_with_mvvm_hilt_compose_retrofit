package com.test.inshortsclone.data.repository

import com.test.inshortsclone.data.entities.CategoryFeedData
import com.test.inshortsclone.data.entities.FeedResponse
import com.test.inshortsclone.data.local.BookmarkDAO
import com.test.inshortsclone.data.remote.FeedRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepositoryImpl
@Inject
constructor(
    private val feedRemoteDataSource: FeedRemoteDataSource,
    private val bookmarkDao: BookmarkDAO
) : FeedRepository {

    override suspend fun getListOfFeedForCategory(category: String): FeedResponse =
        feedRemoteDataSource.getListOfFeedForCategory(category)

    override fun getListOfBookmark(): Flow<MutableList<CategoryFeedData>> =
        bookmarkDao.getBookmarksFormDataBase()

    override suspend fun addBookmark(feed: CategoryFeedData) = bookmarkDao.addBookmark(feed = feed)
    override suspend fun deleteBookmark(feed: CategoryFeedData) = bookmarkDao.deleteBookmark(feed = feed)
    override fun isBookmarked(feedId: String) = bookmarkDao.isBookmarked(feedId)

}