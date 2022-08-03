package com.test.inshortsclone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.inshortsclone.data.entities.CategoryFeedData

@Database(entities = [CategoryFeedData::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bookmarkDao() : BookmarkDAO
}