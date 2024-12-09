package com.example.man2superapp.source.local.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.man2superapp.source.network.service.ApiService
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleApiService: ApiService) : ViewModel() {
    private val _articlesLiveData = MutableLiveData<List<Article>>()
    val articleLiveData: LiveData<List<Article>> = _articlesLiveData

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = articleApiService.getArticles()
                if (response.isSuccessful) {
                    val articles = response.body() ?: emptyList()
                    _articlesLiveData.postValue(articles)
                } else {
                    Log.e("Error", "Failed to fetch articles: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("Exception", "Error fetching articles: $e")
            }
        }
    }
}