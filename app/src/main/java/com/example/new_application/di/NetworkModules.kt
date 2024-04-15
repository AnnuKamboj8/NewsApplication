package com.example.new_application.di

import android.content.Context
import androidx.room.Room
import com.example.new_application.db.ArticleDao
import com.example.new_application.db.ArticleDatabase
import com.example.new_application.repository.NewsRepository
import com.example.new_application.retrofit.response.NewsInterface
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideNewsInterface(retrofit: Retrofit): NewsInterface {    //inject retrofit
        return retrofit.create(NewsInterface::class.java)
    }


    @Singleton
    @Provides
    fun provideRepository(newsInterface: NewsInterface,articleDatabase: ArticleDatabase): NewsRepository {
        return NewsRepository(newsInterface,articleDatabase)
    }

    @Singleton
    @Provides
    fun provideArticleDatabase(
        @ApplicationContext app: Context
    ) :ArticleDatabase  {
        return   Room.databaseBuilder(
            app.applicationContext,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideArticleDao( articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()

    }
}
