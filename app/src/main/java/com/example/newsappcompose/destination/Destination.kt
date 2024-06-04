package com.example.newsappcompose.destination

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Call
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String , val title: String , val icon: ImageVector?){
    object OnBoardingScreen: Destination(route = "OnBoardingScreen" , title = "" , icon = null)

    object NewsScreen: Destination(route = "NewsScreen" , title = "Home" , icon = Icons.Default.Home)
    object Explore: Destination(route = "Explore" , title = "Explore" , icon = Icons.Rounded.Call)

}