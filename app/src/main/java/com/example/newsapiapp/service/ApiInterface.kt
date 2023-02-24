package com.example.newsapiapp.service

import com.example.newsapiapp.model.ApiResponse
import com.example.newsapiapp.model.Source
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v2/everything")
    suspend fun getNewsList(
        @Query("q") q : String,
        @Query("sortBy") sortBy : String,
        @Query("apiKey") apiKey: String
    ) : ApiResponse
}