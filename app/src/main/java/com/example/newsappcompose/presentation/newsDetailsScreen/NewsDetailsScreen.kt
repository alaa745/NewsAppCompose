package com.example.newsappcompose.presentation.newsDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.components.DetailsTopBar
import com.example.newsappcompose.presentation.components.NewsCardForDetails

@Composable
fun NewsDetailsScreen(article: Result , viewModel: NewsDetailsScreenViewModel = hiltViewModel()) {
    val isSaved = viewModel.isSaved.observeAsState().value

    LaunchedEffect(article.article_id){
        viewModel.checkArticleExistsForFirstTime(article)
    }
    Scaffold(
        topBar = {
            DetailsTopBar(
                onBackClicked = { /*TODO*/ },
                onShareClicked = { /*TODO*/ },
                result = article,
                onSaveClicked = {
                    viewModel.insertOrDeleteArticle(article)
                }) {
            }
        }
    ) { padding ->
        NewsCardForDetails(
            result = article,
            paddingValues = padding
        )
    }
}
