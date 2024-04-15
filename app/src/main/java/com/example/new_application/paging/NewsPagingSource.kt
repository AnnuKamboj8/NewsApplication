package com.example.new_application.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.new_application.Constant
import com.example.new_application.retrofit.response.Article
import com.example.new_application.retrofit.response.NewsInterface
import retrofit2.HttpException
import java.io.IOException

const val  STARTING_INDEX = 1

class NewsPagingSource(private val  newsInterface: NewsInterface):PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_INDEX
        return try {
            val data = newsInterface.getAllNews(
                "in",
                "business",
                Constant.API_KEY,
                position,
                params.loadSize)
            LoadResult.Page(
                data = data.articles,
                prevKey = if (params.key == STARTING_INDEX) null else position - 1,
                nextKey = if (data.articles.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)

        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }
}
