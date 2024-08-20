package com.example.newsappcompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "articles")
@TypeConverters()
data class Result(
//    val ai_org: String,
//    val ai_region: String,
//    val ai_tag: String,
    @PrimaryKey
    val article_id: String,
    val category: List<String>? = emptyList(),
    val content: String?,
    val country: List<String>? = emptyList(),
    val creator: List<String>? = emptyList(),
    val description: String?,
    val image_url: String?,
    val keywords: List<String>? = emptyList(),
    val language: String?,
    val link: String?,
    val pubDate: String?,
    val source_icon: String?,
    val source_id: String?,
    val source_priority: Int?,
    val source_url: String?,
    val title: String?,
    val video_url: Any? = null
): Serializable