package com.example.newsappcompose.presentation.homeNewsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsappcompose.R
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.presentation.components.CategorySelector
import com.example.newsappcompose.presentation.components.NewsCard
import com.example.newsappcompose.presentation.components.SearchBar
import com.example.newsappcompose.presentation.components.VerticalNewsCard
import kotlinx.coroutines.flow.collect
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isTopLoading by viewModel.isTopLoading.collectAsState()

    val isSearching by viewModel.isSearching.collectAsState()
    var isSearch = isSearching
    val isError by viewModel.isError.collectAsState()
    val isTopError by viewModel.isTopError.collectAsState()
    val topError by viewModel.topError.collectAsState()

    val error by viewModel.error.collectAsState()
    val categories = listOf("All" , "Sports", "Education", "Technology", "Health" , "Politics" , "Travel" , "Science")
    var selectedCategory by remember {
        mutableStateOf(categories.first())
    }
    var selectedCategoryForApi: String by remember {
        mutableStateOf("")
    }
    var searchText: String? by remember {
        mutableStateOf(null)
    }
    var searchTextForApi: String? by remember {
        mutableStateOf(null)
    }
//    var isSearching by remember {
//        mutableStateOf(false)
//    }
    val topNewsFlow = viewModel.getTopNews(category = listOf("top") , searchText = searchTextForApi)
    val topArticles = topNewsFlow.collectAsState(initial = null)
    val newsFlow = viewModel.getNews(category = listOf(selectedCategoryForApi) , searchText = searchTextForApi)
    val articles = newsFlow.collectAsLazyPagingItems()



    Scaffold(
        topBar = {
//            TopAppBar()
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 10.dp , end = 10.dp)
//            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 15.dp, start = 10.dp)
            )
            SearchBar(
                onValueChange = {
                    searchText = it
                },
                value = searchText ?: "",
            ){
                searchText = ""
                searchTextForApi = it
//                isSearch = true
//                viewModel.getNews(searchText = searchTextForApi)
//                searchTextForApi = ""h
                println("Search $searchTextForApi")
            }
            Spacer(modifier = Modifier.height(10.dp))
            when {
                isTopLoading -> {
                    println("loading toppp")
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF1877F2)
                    )
                }
                isTopError -> {
                    println("top errorr")
                    ErrorView(error = error)
                }
                else  -> {
                    VerticalNewsCard(
                        title = topArticles.value?.results?.first()?.title,
                        image = topArticles.value?.results?.first()?.image_url,
                        source = topArticles.value?.results?.first()?.source_id,
                        category = topArticles.value?.results?.first()?.category?.first(),
                        sourceIcon = topArticles.value?.results?.first()?.source_icon,
                    )
//                    LazyColumn(
//                        contentPadding = innerPadding,
////                modifier = Modifier.fillMaxSize()
//
//                    ) {
//                        items(articles.itemCount) {
//                            articles[it].let { article ->
//                                NewsCard(
//                                    title = article?.title,
//                                    image = article?.image_url,
//                                    source = article?.source_id,
//                                    category = article?.category?.first(),
//                                    sourceIcon = article?.source_icon,
//                                )
//                            }
//                        }
//                    }
                }
            }
            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
            ) {
                selectedCategory = it
                if (it.lowercase() == "All".lowercase(Locale.ROOT))
                    selectedCategoryForApi = ""
                else
                    selectedCategoryForApi = it
                println("category $it")
            }
            when {
                isLoading -> {
                    println("loadinggggg")
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF1877F2)
                        )
                }
                isError -> {
                    println("errrorrrr")
                    ErrorView(error = error)
                }
                else -> {
                    LazyColumn(
                        contentPadding = innerPadding,
//                modifier = Modifier.fillMaxSize()

                    ) {
                        items(articles.itemCount) {
                            articles[it].let { article ->
                                NewsCard(
                                    title = article?.title,
                                    image = article?.image_url,
                                    source = article?.source_id,
                                    category = article?.category?.first(),
                                    sourceIcon = article?.source_icon,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ErrorView(error: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = error ?: "An unknown error occurred", color = Color.Red)
        Button(onClick = { /* Retry logic */ }) {
            Text(text = "Retry")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(
        {

        },
//       windowInsets = TopAppBarDefaults.,

        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 15.dp, start = 10.dp)
            )
        }
    )
}

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val selectedScreen by remember {
        mutableStateOf(0)
    }
    val items = listOf(
        Destination.NewsScreen,
        Destination.Explore
    )
    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedScreen == index,
                onClick = { /*TODO*/ },
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    Icon(screen.icon!!, contentDescription = null)
                }
            )
        }
    }
}