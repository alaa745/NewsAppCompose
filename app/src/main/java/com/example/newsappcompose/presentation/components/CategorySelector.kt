package com.example.newsappcompose.presentation.components

import android.provider.CalendarContract.Colors
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsappcompose.R

@Composable
fun CategorySelector(categories: List<String> , selectedCategory: String , isScrolled: Boolean , onCategorySelected: (String) -> Unit ){
    var textWidth by remember { mutableStateOf(0) }
    var measured by remember { mutableStateOf(false) }

    LazyRow(
        contentPadding = PaddingValues(start = 10.dp , end = 10.dp),
        modifier = if (isScrolled)  Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding() else Modifier
            .background(color = MaterialTheme.colorScheme.background)
            , horizontalArrangement = Arrangement.SpaceEvenly) {
        items(categories) { category ->
            Column(modifier = Modifier.padding(8.dp).wrapContentWidth() , verticalArrangement = Arrangement.SpaceBetween , horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    //                maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
//                    color = colorResource(id = R.color.display_small),
                    modifier = Modifier.clickable {
                        onCategorySelected(category)
                    }.onGloballyPositioned {
                        if (!measured) {
                            textWidth = it.size.width
                            measured = true
                        }
                    }
                )
                    val color by animateColorAsState(
                        targetValue = if (category == selectedCategory) Color(0xFF1877F2) else Color.Transparent
                    )
                    val thickness by animateDpAsState(
                        targetValue = if (category == selectedCategory) 3.dp else 0.dp
                    )
                if (category == selectedCategory && measured)
                    HorizontalDivider(
                        modifier = Modifier.size(width = textWidth.dp , height = 20.dp),
                        thickness = thickness,
                        color = color
                    )


            }
        }

    }

}
