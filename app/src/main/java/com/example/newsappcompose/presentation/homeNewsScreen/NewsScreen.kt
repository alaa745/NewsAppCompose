package com.example.newsappcompose.presentation.homeNewsScreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsappcompose.R
import com.example.newsappcompose.data.local.NewsDao
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.presentation.components.CategorySelector
import com.example.newsappcompose.presentation.components.NewsCard
import com.example.newsappcompose.presentation.components.SearchBar
import com.example.newsappcompose.presentation.components.VerticalNewsCard
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(viewModel: HomeScreenViewModel = hiltViewModel(), navHostController: NavHostController) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isTopLoading by viewModel.isTopLoading.collectAsState()

    val isError by viewModel.isError.collectAsState()
    val isTopError by viewModel.isTopError.collectAsState()
    val topError by viewModel.topError.collectAsState()
    val error by viewModel.error.collectAsState()

    val categories = listOf("All", "Sports", "Education", "Technology", "Health", "Politics", "Travel", "Science")
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var selectedCategoryForApi by remember { mutableStateOf("") }
    var searchText by remember { mutableStateOf<String?>(null) }
    var searchTextForApi by remember { mutableStateOf<String?>(null) }

    val topNewsFlow = viewModel.getTopNews(category = listOf("top"))
    val topArticles = topNewsFlow.collectAsState(initial = null)
    val newsFlow = viewModel.getNews(category = listOf(selectedCategoryForApi), searchText = searchTextForApi)
    val articles = newsFlow.collectAsLazyPagingItems()

    // Track scroll position using LazyColumn's scroll state
    val scrollState = rememberLazyListState()
    val showCategoryOnTop by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0
        }
    }

    Scaffold {
        LazyColumn(
            state = scrollState,
//            modifier = Modifier.fillMaxSize(),
        ) {
            // Top section with Image and SearchBar, this will scroll
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 15.dp, start = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SearchBar(
                        onValueChange = { searchText = it },
                        value = searchText ?: "",
                        onSearch = {
                            searchTextForApi = searchText
                        }
                    )
//                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            // Display content - top article and news articles
            item {
                when {
                    isTopLoading -> {
                        println("top loadingggggg")
                        ShimmerNewsCard()

                    }
                    isTopError -> {
                        ErrorView(error = topError)
                    }
                    else -> {
                        topArticles.value?.results?.firstOrNull()?.let { article ->
                            VerticalNewsCard(
                                title = article.title,
                                image = article.image_url,
                                source = article.source_id,
                                category = article.category?.first(),
                                sourceIcon = article.source_icon
                            )
                        }
                    }
                }
            }
            // StickyHeader for CategorySelector - stays at the top when scrolling
            stickyHeader {
                CategorySelector(
                    categories = categories,
                    isScrolled = showCategoryOnTop,
                    selectedCategory = selectedCategory
                ) {
                    selectedCategory = it
                    selectedCategoryForApi = if (it.lowercase(Locale.ROOT) == "all") "" else it
                }
                // Add padding below the CategorySelector to separate it from the list
                Spacer(modifier = Modifier.height(10.dp))
            }
            when{
                isLoading -> {
                    item {
                        ShimmerNewsCard()
                    }
                }
                isError -> {
                    item {
                        ErrorView(error = error)
                    }
                }
                else -> {
                    println("elseeeee")
                    // Load list of news articles
                    items(articles.itemCount) { index ->
                        articles[index]?.let { article ->
                            NewsCard(
                                result = article,
                                onClick = {
                                    val articleJson = Gson().toJson(article)
                                    val encodedArticleJson = URLEncoder.encode(articleJson, StandardCharsets.UTF_8.toString())
                                    navHostController.navigate("${Destination.NewsDetailsScreen.route}/$encodedArticleJson")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .background(brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.6f),
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 0.6f)
                ),
                start = Offset(shimmerTranslate - 1000f, 0f),
                end = Offset(shimmerTranslate, 0f)
            ))
    )
}
@Composable
fun ShimmerNewsCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(5.dp)
    ) {
        ShimmerEffect(
            modifier = Modifier
                .size(width = 96.dp, height = 96.dp)
                .clip(RoundedCornerShape(8))
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(7.dp)
                .height(105.dp)
        ) {
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShimmerEffect(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(15.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(5.dp))
                ShimmerEffect(
                    modifier = Modifier
                        .size(width = 10.dp, height = 10.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(15.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
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
fun BottomNavigationBar(navHostController: NavHostController) {
    val navController = rememberNavController()
    var selectedScreen by remember {
        mutableStateOf(0)
    }
    val items = listOf(
        Destination.NewsScreen,
        Destination.Saved
    )
    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedScreen == index,
                onClick = {
                    selectedScreen = index
                    if (selectedScreen == 0)
                        return@NavigationBarItem
                    else
                        navHostController.navigate(Destination.Saved.route)
                },
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