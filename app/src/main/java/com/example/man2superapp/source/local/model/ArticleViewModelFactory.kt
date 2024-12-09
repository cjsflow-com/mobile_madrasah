package com.example.man2superapp.source.local.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.man2superapp.source.network.service.ApiService

class ArticleViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}