package com.example.newsappcompose.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsappcompose.R
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.newsDetailsScreen.NewsDetailsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    viewModel: NewsDetailsScreenViewModel = hiltViewModel(),
    result: Result,
    onBackClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onBrowsingClicked: () -> Unit,
){
    val isSaved = viewModel.isSaved.observeAsState().value
    val isExist = viewModel.isExist.observeAsState().value

//    println("saved ${isSaved.value}")

    TopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body),
        ),
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onSaveClicked) {
                Icon(if (isSaved == true || isExist == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = null)
            }
            IconButton(onClick = onShareClicked) {
                Icon(Icons.Filled.Share, contentDescription = null)
            }
            IconButton(onClick = onBrowsingClicked) {
                Icon(painter =  painterResource(id = R.drawable.ic_browsing), contentDescription = null)
            }
        }
    )
}