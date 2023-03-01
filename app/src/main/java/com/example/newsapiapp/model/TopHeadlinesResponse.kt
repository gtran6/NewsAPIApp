package com.example.newsapiapp.model

data class TopHeadlinesResponse(
    val articles: List<ArticleX>,
    val status: String,
    val totalResults: Int
)