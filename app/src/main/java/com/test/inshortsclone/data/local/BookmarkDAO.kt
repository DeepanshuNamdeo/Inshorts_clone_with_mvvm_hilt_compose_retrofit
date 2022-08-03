package com.test.inshortsclone.data.local

import androidx.room.*
import com.test.inshortsclone.data.entities.CategoryFeedData
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDAO {

    @Query("SELECT * FROM categoryfeeddata")
    fun getBookmarksFormDataBase() : Flow<MutableList<CategoryFeedData>>


    @Delete
    suspend fun deleteBookmark(feed : CategoryFeedData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(feed : CategoryFeedData)

    @Query("SELECT EXISTS(SELECT * FROM categoryfeeddata WHERE id = :feedId)")
    fun isBookmarked(feedId: String) : Boolean
}
