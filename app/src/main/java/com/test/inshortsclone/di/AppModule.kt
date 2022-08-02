package com.test.inshortsclone.di

import com.test.inshortsclone.data.remote.ApiService
import com.test.inshortsclone.data.remote.FeedRemoteDataSource
import com.test.inshortsclone.data.remote.FeedRemoteDataSourceImpl
import com.test.inshortsclone.data.repository.FeedRepository
import com.test.inshortsclone.data.repository.FeedRepositoryImpl
import com.test.inshortsclone.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideFeedRemoteDataSource( feedRemoteDataSource : FeedRemoteDataSourceImpl): FeedRemoteDataSource =
        feedRemoteDataSource

    @Provides
    fun provideFeedRepository(feedRepository: FeedRepositoryImpl) : FeedRepository = feedRepository


}