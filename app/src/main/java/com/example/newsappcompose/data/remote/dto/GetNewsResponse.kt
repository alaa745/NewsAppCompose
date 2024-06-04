package com.example.newsappcompose.data.remote.dto

import com.example.newsappcompose.domain.model.Result

data class GetNewsResponse(
    val nextPage: String,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)