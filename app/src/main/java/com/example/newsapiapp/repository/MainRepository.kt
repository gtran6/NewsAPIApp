package com.example.newsapiapp.repository

import android.content.Context
import com.example.newsapiapp.data.NewsDao
import com.example.newsapiapp.data.NewsDatabase
import com.example.newsapiapp.model.ApiResponse
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.model.TopHeadlinesResponse
import com.example.newsapiapp.service.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(val apiInterface: ApiInterface, val newsDao: NewsDao) {

    fun getNews(q: String, sortBy: String, apiKey: String) : Flow<ApiResponse> = flow {
        emit(apiInterface.getNewsList(q, sortBy, apiKey))
    }

    fun getTopHeadlines(sources: String, apiKey: String) : Flow<TopHeadlinesResponse> = flow {
        emit(apiInterface.getTopNewsList(sources, apiKey))
    }

    // get all news
    fun getAllNews(context: Context) : Flow<List<Article>> {
        var newsDatabase: NewsDatabase = NewsDatabase.getDatabaseClient(context)
        return newsDatabase!!.newsDao().getNewsFromDatabase()
    }

    // insert news
    fun insertNews(context: Context, article: Article) {
        var newsDatabase: NewsDatabase = NewsDatabase.getDatabaseClient(context)
        CoroutineScope(IO).launch {
            newsDatabase!!.newsDao().insertNews(article)
        }
    }

    // delete news
    fun deleteNews(context: Context, article: Article) {
        var newsDatabase: NewsDatabase = NewsDatabase.getDatabaseClient(context)
        CoroutineScope(IO).launch {
            newsDatabase!!.newsDao().deleteNews(article)
        }
    }
}