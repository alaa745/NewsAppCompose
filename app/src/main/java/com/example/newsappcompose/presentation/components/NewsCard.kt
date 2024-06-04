package com.example.newsappcompose.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.newsappcompose.presentation.onBoarding.pages
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import java.util.Date


@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    image: String? = null,
    source: String? = null,
    category: String?= null,
    sourceIcon: String? = null,
    date: Date? = null,
){
    Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(5.dp)) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 96.dp, height = 96.dp)
                .clip(RoundedCornerShape(8))
        )
        Column (verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(7.dp)){
            Text(
                text = category ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = colorResource(id = R.color.text_medium)
            )
            Text(
                text = title ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.display_small)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = sourceIcon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 20.dp, height = 20.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = source ?: "",
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
                    text = "4h ago",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.text_medium),
                    modifier = Modifier.padding(start = 5.dp)

                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun previewCard(){
    NewsAppComposeTheme {
        NewsCard()
    }
}