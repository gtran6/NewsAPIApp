package com.example.newsapiapp.data

import androidx.room.*
import com.example.newsapiapp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(article: Article)

    @Delete
    suspend fun deleteNews(article: Article)

    @Query("SELECT * FROM news_table")
    fun getNewsFromDatabase() : Flow<List<Article>>

}