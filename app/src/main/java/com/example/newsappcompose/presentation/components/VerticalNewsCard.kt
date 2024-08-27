package com.example.newsappcompose.presentation.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bumptech.glide.load.resource.drawable.DrawableResource
import com.example.newsappcompose.R
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun VerticalNewsCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    image: String? = null,
    source: String? = null,
    category: String?= null,
    sourceIcon: String? = null,
    date: String? = null,
    @DrawableRes placeHolderImage: Int?
){
    Column(modifier = modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        if (!image.isNullOrEmpty()){
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.Low,
                modifier = Modifier
//                .fillw()
                    .width(400.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(8))
            )
        } else {
            Image(
                painter = painterResource(id = placeHolderImage!!),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =  Modifier.width(400.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(8))
            )
        }

        Text(
            text = category ?: "",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            ),
        )
        Box(
            Modifier.width(
                400.dp
            )
        ) {
            Text(
                text = title ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                //            color = colorResource(id = R.color.display_small)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically , modifier = modifier.padding(8.dp)) {
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
                text = getNewsDate(date!!)!!,
                style = MaterialTheme.typography.labelSmall,
                color = colorResource(id = R.color.text_medium),
                modifier = Modifier.padding(start = 5.dp)

            )
        }
    }
}


//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun previewVerticalCard(){
//    NewsAppComposeTheme {
//        VerticalNewsCard()
//    }
//}