package com.example.newsappcompose.destination

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String , val title: String , val icon: ImageVector?){
    object OnBoardingScreen: Destination(route = "OnBoardingScreen" , title = "" , icon = null)

    object NewsScreen: Destination(route = "NewsScreen" , title = "Home" , icon = Icons.Default.Home)
    object Saved: Destination(route = "Saved" , title = "Saved" , icon = Icons.Rounded.Favorite)

    object SettingsScreen: Destination(route = "Settings" , title = "Settings" , icon = Icons.Rounded.Settings)

    object NewsDetailsScreen: Destination(route = "NewsDetailsScreen" , title = "" , icon = null)

}