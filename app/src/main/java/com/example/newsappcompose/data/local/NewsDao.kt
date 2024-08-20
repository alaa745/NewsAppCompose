package com.example.newsappcompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsappcompose.domain.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: Result)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Result>>

    @Query("SELECT * FROM articles WHERE article_id = :id")
    suspend fun getArticleById(id: String): Result

    @Query("DELETE FROM articles WHERE article_id = :id")
    suspend fun deleteResultById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE article_id = :id)")
    suspend fun isArticleExists(id: String): Boolean
}