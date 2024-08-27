package com.example.newsappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.destination.Destination.NewsScreen
import com.example.newsappcompose.destination.Destination.OnBoardingScreen
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.homeNewsScreen.NewsScreen
import com.example.newsappcompose.presentation.newsDetailsScreen.NewsDetailsScreen
import com.example.newsappcompose.presentation.savedNewsScreen.SavedNewsScreen
import com.example.newsappcompose.presentation.settingsScreen.SettingsScreen
import com.example.newsappcompose.presentation.settingsScreen.SettingsScreenViewModel
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val viewModel: SettingsScreenViewModel = viewModel()
            val isDarkMode = viewModel.isDarkMode.observeAsState()
            NewsAppComposeTheme(
                darkTheme = isDarkMode.value!!,
            ) {
//                val isDarkMode = isSystemInDarkTheme()
                val context = LocalContext.current
                val view = LocalView.current
                // Make status bar and navigation bar transparent
                val windowInsetsController = ViewCompat.getWindowInsetsController(view)
                windowInsetsController?.isAppearanceLightStatusBars = !isDarkMode.value!! // Dark text in light mode
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT


                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(settingsScreenViewModel: SettingsScreenViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    println("Current Route $currentRoute")
    val bottomNavExcludedRoutes = listOf(
        OnBoardingScreen.route,
//        NewsScreen.route,
//       Destination.Saved.route,
        Destination.NewsDetailsScreen.route// Ad // d the routes where you want to hide the BottomNavigationBar
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            ,
        bottomBar = {
            if (bottomNavExcludedRoutes.none { currentRoute?.startsWith(it) == true })
                BottomNavigationBar(navController)
        }
    ) {
        MyNavigation(navController , settingsScreenViewModel)
    }
}

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    var selectedScreen by remember {
        mutableStateOf(0)
    }
    val items = listOf(
        NewsScreen,
        Destination.Saved,
        Destination.SettingsScreen
    )
    NavigationBar {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
//                    selectedScreen = index
                    navHostController.navigate(screen.route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }                },
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

@Composable
fun MyNavigation(navController: NavHostController, settingsScreenViewModel: SettingsScreenViewModel = viewModel()){
    NavHost(navController = navController, startDestination = NewsScreen.route) {
//        composable(OnBoardingScreen.route){
//            OnBoardingScreen(navController)
//        }
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

            NewsDetailsScreen(article = article , navHostController = navController)
        }
        composable(Destination.Saved.route){
            SavedNewsScreen(navHostController = navController)
        }
        composable(Destination.SettingsScreen.route){
            SettingsScreen(viewModel = settingsScreenViewModel)
        }
    }
}