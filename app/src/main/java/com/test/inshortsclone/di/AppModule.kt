package com.test.inshortsclone.di

import android.content.Context
import androidx.room.Room
import com.test.inshortsclone.data.local.AppDatabase
import com.test.inshortsclone.data.local.BookmarkDAO
import com.test.inshortsclone.data.remote.ApiService
import com.test.inshortsclone.data.remote.FeedRemoteDataSource
import com.test.inshortsclone.data.remote.FeedRemoteDataSourceImpl
import com.test.inshortsclone.data.repository.FeedRepository
import com.test.inshortsclone.data.repository.FeedRepositoryImpl
import com.test.inshortsclone.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Singleton
    @Provides
    fun providesRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "App_database").allowMainThreadQueries().build()
    }

    //locale dependencies
    @Provides
    fun provideBookmarkDao(appDatabase: AppDatabase): BookmarkDAO = appDatabase.bookmarkDao()


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideFeedRemoteDataSource( feedRemoteDataSource : FeedRemoteDataSourceImpl): FeedRemoteDataSource =
        feedRemoteDataSource

    @Provides
    fun provideFeedRepository(feedRepository: FeedRepositoryImpl) : FeedRepository = feedRepository


}