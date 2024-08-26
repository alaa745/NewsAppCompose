package com.example.newsappcompose.presentation.newsDetailsScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.components.DetailsTopBar
import com.example.newsappcompose.presentation.components.NewsCardForDetails

@Composable
fun NewsDetailsScreen(article: Result , viewModel: NewsDetailsScreenViewModel = hiltViewModel() , navHostController: NavHostController) {
    val isSaved = viewModel.isSaved.observeAsState().value
    val context = LocalContext.current
    val scrollableState = rememberScrollState()
    LaunchedEffect(article.article_id){
        viewModel.checkArticleExistsForFirstTime(article)
    }
    Scaffold(
        topBar = {
            DetailsTopBar(
                onBackClicked = {
                    navHostController.popBackStack()
                },
                onShareClicked = {
                    article.link?.let {
                        shareLink(context = context , url = it)
                    }
                },

                result = article,
                onSaveClicked = {
                    viewModel.insertOrDeleteArticle(article)
                }) {
                article.link?.let {
                    openUrl(context = context , url = it)
                }
            }
        }
    ) { padding ->
        NewsCardForDetails(
            result = article,
            paddingValues = padding
        )
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    context.startActivity(intent)
}

fun shareLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"  // Set the MIME type to text/plain for sharing text
        putExtra(Intent.EXTRA_TEXT, url)
    }

    // Create a chooser intent to let the user select the app to share with
    val shareIntent = Intent.createChooser(intent, "Share link via")

    // Start the chooser activity
    context.startActivity(shareIntent)
}

