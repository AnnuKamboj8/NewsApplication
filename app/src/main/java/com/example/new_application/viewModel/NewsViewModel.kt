package com.example.new_application.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.new_application.repository.NewsRepository

import com.example.new_application.retrofit.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel  @Inject constructor(private var newsRepository: NewsRepository):ViewModel(){

    val list :LiveData<PagingData<Article>> = newsRepository.getAllNewsStream().cachedIn(viewModelScope)


    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.insert(article)

    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.deleteArticle(article)
    }

}