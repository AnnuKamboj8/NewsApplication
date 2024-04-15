package com.example.new_application.db

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.new_application.retrofit.response.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    @NonNull
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM article")
    fun getAllArticles():LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}