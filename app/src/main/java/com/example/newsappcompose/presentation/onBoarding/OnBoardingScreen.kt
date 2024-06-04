package com.example.newsappcompose.presentation.onBoarding

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.presentation.components.OnBoardingButton
import com.example.newsappcompose.presentation.components.OnBoardingPage
import com.example.newsappcompose.presentation.components.PageIndicator
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    Column(modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val scope = rememberCoroutineScope()
        HorizontalPager(state = pagerState) { inedx ->
            OnBoardingPage(page = pages[inedx])
        }

        val buttonText = remember {
            derivedStateOf {
                when (pagerState.currentPage){
                    0 -> "Next"
                    1 -> "Next"
                    2 -> "Get Started"
                    else -> ""
                }
            }
        }

        Spacer(modifier = modifier.weight(1f))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PageIndicator(
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )
            OnBoardingButton(text = buttonText.value) {
                scope.launch {
                    pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                }
                if (pagerState.currentPage == 2)
                    navController.navigate(Destination.NewsScreen.route){
                        popUpTo(Destination.OnBoardingScreen.route){
                            inclusive = true
                        }
                    }
            }
        }
    }
}