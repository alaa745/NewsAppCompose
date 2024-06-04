package com.example.newsappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsappcompose.destination.Destination.NewsScreen
import com.example.newsappcompose.destination.Destination.OnBoardingScreen
import com.example.newsappcompose.presentation.components.OnBoardingPage
import com.example.newsappcompose.presentation.homeNewsScreen.NewsScreen
import com.example.newsappcompose.presentation.onBoarding.OnBoardingScreen
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

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
            NewsScreen()
        }
    }
}