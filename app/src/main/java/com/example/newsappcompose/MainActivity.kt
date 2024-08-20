package com.example.newsappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.destination.Destination.NewsScreen
import com.example.newsappcompose.destination.Destination.OnBoardingScreen
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.homeNewsScreen.NewsScreen
import com.example.newsappcompose.presentation.newsDetailsScreen.NewsDetailsScreen
import com.example.newsappcompose.presentation.onBoarding.OnBoardingScreen
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb() , Color.Transparent.toArgb()))
//        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NewsAppComposeTheme {
                val isDarkMode = isSystemInDarkTheme()
                val context = LocalContext.current
                val view = LocalView.current
                ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
                    val controller = WindowInsetsControllerCompat(window, view)
                    controller.hide(WindowInsetsCompat.Type.systemBars())
                    insets
                }
                MyNavigation()
            }
        }
    }
}

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = OnBoardingScreen.route) {
        composable(OnBoardingScreen.route){
            OnBoardingScreen(navController)
        }
        composable(NewsScreen.route){
            NewsScreen(navHostController = navController)
        }
        composable(
            route = "${Destination.NewsDetailsScreen.route}/{articleJson}",
            arguments = listOf(
//                navArgument("articleContent") { type = NavType.StringType },
                navArgument("articleJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
//            val articleContent = backStackEntry.arguments?.getString("articleContent")
            val encodedArticleJson = backStackEntry.arguments?.getString("articleJson")
            val articleDecodeJson = URLDecoder.decode(encodedArticleJson, StandardCharsets.UTF_8.toString())
            val article = Gson().fromJson(articleDecodeJson, Result::class.java)

            NewsDetailsScreen(article)
        }
    }
}