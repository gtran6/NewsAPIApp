package com.example.newsapiapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapiapp.extra.Events
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {
    val data : MutableLiveData<Events<List<Article>>> = MutableLiveData()

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

}