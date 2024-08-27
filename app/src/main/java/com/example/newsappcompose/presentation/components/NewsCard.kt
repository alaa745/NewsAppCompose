package com.example.newsappcompose.presentation.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.newsappcompose.R
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.onBoarding.pages
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import java.time.LocalDateTime
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    result: Result,
    @DrawableRes placeHolderImage: Int?,
    onClick: (String) -> Unit
){
    Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(5.dp).clickable {
        onClick(result.description ?: "")
    }) {
        if (!result.image_url.isNullOrEmpty()){
            AsyncImage(
                model = result.image_url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.Low,
                modifier = Modifier
                    .size(width = 96.dp, height = 96.dp)
                    .clip(RoundedCornerShape(8))
            )
        } else {
            Image(
                painter = painterResource(id = placeHolderImage!!),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =  Modifier.size(width = 96.dp, height = 96.dp)
                .clip(RoundedCornerShape(8))
            )
        }

        Column (verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(7.dp).height(105.dp)){
            Text(
                text = result.category?.first() ?: "",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colorResource(id = R.color.text_medium)
            )
            Text(
                text = result.title ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = result.source_icon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 20.dp, height = 20.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = result.source_id ?: "",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.text_medium),
                    modifier = Modifier.padding(start = 5.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.clock_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(width = 10.dp, height = 10.dp)

                )
                Text(
                    text = getNewsDate(result.pubDate!!)!!,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.text_medium),
                    modifier = Modifier.padding(start = 5.dp)

                )
            }
        }
    }
}


fun getNewsDate(dateString: String): String? {
    // Define the custom formatter for the input string
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Parse the string into LocalDateTime using the custom formatter
    val localDateTime = LocalDateTime.parse(dateString, formatter)

    // Define the output formatter (desired output format)
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Format and return the date in 'yyyy-MM-dd' format
    return localDateTime.format(outputFormatter)
}