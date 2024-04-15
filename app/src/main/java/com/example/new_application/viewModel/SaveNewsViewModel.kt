package com.example.new_application.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.new_application.repository.NewsRepository

import com.example.new_application.retrofit.response.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SaveNewsViewModel @Inject constructor(newsRepository: NewsRepository):ViewModel(){

    val list: LiveData<List<Article>> =
        newsRepository.getSavedNews()


}