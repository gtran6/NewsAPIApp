package com.example.newsapiapp.di

import android.content.Context
import com.example.newsapiapp.data.NewsDao
import com.example.newsapiapp.data.NewsDatabase
import com.example.newsapiapp.extra.Utils.BASE_URL
import com.example.newsapiapp.repository.MainRepository
import com.example.newsapiapp.service.ApiInterface
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
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(apiInterface: ApiInterface, newsDao: NewsDao) : MainRepository = MainRepository(apiInterface, newsDao)

    @Singleton
    @Provides
    fun provideRetrofitInstance() : ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = NewsDatabase.getDatabaseClient(appContext)
}