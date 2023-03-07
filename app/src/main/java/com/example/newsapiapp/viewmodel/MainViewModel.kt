package com.example.newsapiapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.extra.Events
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.model.ArticleX
import com.example.newsapiapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {
    val data : MutableLiveData<Events<List<Article>>> = MutableLiveData()
    val headlinesResponse: MutableLiveData<Events<List<ArticleX>>> = MutableLiveData()

    fun getData(q: String, sortBy: String, apiKey: String) {
        viewModelScope.launch {
            data.postValue(Events.Loading())
            mainRepository.getNews(q, sortBy, apiKey).catch {
                Log.e("API", "get: ${it.localizedMessage}")
                data.postValue(Events.Error(msg = it.localizedMessage))
            }.collect { list ->
                data.postValue(Events.Success(list.articles))
            }
        }
    }

    fun getDataTopHeadlines(source: String, apiKey: String) {
        viewModelScope.launch {
            headlinesResponse.postValue(Events.Loading())
            mainRepository.getTopHeadlines(source, apiKey).catch {
                Log.e("API", "get: ${it.localizedMessage}")
                headlinesResponse.postValue(Events.Error(msg = it.localizedMessage))
            }.collect { list ->
                headlinesResponse.postValue(Events.Success(list.articles))
            }
        }
    }

    fun insertNews(context: Context, article: Article) {
        mainRepository.insertNews(context, article)
    }

    fun getNewsFromDatabase(context: Context) {
        //mainRepository.getAllNews(context)
        viewModelScope.launch {
            data.postValue(Events.Loading())
            mainRepository.getAllNews(context).catch {
                Log.e("API", "get: ${it.localizedMessage}")
                data.postValue(Events.Error(msg = it.localizedMessage))
            }.collect { list ->
                data.postValue(Events.Success(list))
            }
        }
    }

    fun deleteNews(context: Context, article: Article) {
        mainRepository.deleteNews(context, article)
    }
}