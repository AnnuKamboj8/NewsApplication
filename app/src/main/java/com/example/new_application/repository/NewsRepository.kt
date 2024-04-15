package com.example.new_application.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.new_application.db.ArticleDao
import com.example.new_application.db.ArticleDatabase
import com.example.new_application.paging.NewsPagingSource
import com.example.new_application.retrofit.response.Article
import com.example.new_application.retrofit.response.NewsInterface
import dagger.Provides



class NewsRepository(private val newsInterface: NewsInterface, private val articleDatabase: ArticleDatabase) {

     suspend fun insert(article: Article) = articleDatabase.getArticleDao().insert(article)

    fun getSavedNews() = articleDatabase.getArticleDao().getAllArticles()

     suspend fun deleteArticle(article: Article) =articleDatabase.getArticleDao().deleteArticle(article)

    fun getAllNewsStream(): LiveData<PagingData<Article>> = Pager(
        config = PagingConfig(20, 5, enablePlaceholders = false),
        pagingSourceFactory = {
            NewsPagingSource(newsInterface)
        }
    ).liveData

}
