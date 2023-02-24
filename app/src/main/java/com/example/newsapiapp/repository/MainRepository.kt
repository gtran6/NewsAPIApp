package com.example.newsapiapp.repository

import com.example.newsapiapp.model.ApiResponse
import com.example.newsapiapp.service.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(val apiInterface: ApiInterface) {

    fun getNews(q: String, sortBy: String, apiKey: String) : Flow<ApiResponse> = flow {
        emit(apiInterface.getNewsList(q, sortBy, apiKey))
    }
}