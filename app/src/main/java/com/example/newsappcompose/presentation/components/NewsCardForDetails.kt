package com.example.newsappcompose.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsappcompose.R
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import java.util.Date

@Composable
fun NewsCardForDetails(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    result: Result? = null
){
    val scrollableState = rememberScrollState()
    Column(modifier = modifier
        .padding(paddingValues)
        .padding(16.dp)
        .verticalScroll(scrollableState)
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically , modifier = modifier.padding(bottom = 10.dp)) {
            AsyncImage(
                model = result?.source_icon,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    .clip(CircleShape)
            )
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = result?.source_id ?: "",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.text_medium),
                    modifier = Modifier.padding(start = 5.dp)
                )
                Text(
                    text = "4h ago",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.text_medium),
                    modifier = Modifier.padding(start = 5.dp)

                )
            }

        }
        AsyncImage(
            model = result?.image_url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8))
        )
        Spacer(modifier = modifier.height(10.dp))

        Text(
            text = result?.category?.first() ?: "",
            style = MaterialTheme.typography.labelSmall,
//            color = colorResource(id = R.color.text_medium)
        )
        Text(
            text = result?.title ?: "",
            style = MaterialTheme.typography.displayMedium,
            maxLines = 4,
//            overflow = TextOverflow.Ellipsis,
//            color = colorResource(id = R.color.display_small)
        )
        Spacer(modifier = modifier.height(15.dp))

        Text(
            text = result?.description ?: "",
            style = MaterialTheme.typography.displaySmall,
//            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
//            color = colorResource(id = R.color.body)
        )
//        Row(verticalAlignment = Alignment.CenterVertically , modifier = modifier.padding(8.dp)) {
//            AsyncImage(
//                model = result?.source_icon,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(width = 20.dp, height = 20.dp)
//                    .clip(CircleShape)
//            )
//            Text(
//                text = result?.source_id ?: "",
//                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
//                color = colorResource(id = R.color.text_medium),
//                modifier = Modifier.padding(start = 5.dp)
//            )
//            Image(
//                painter = painterResource(id = R.drawable.clock_icon),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .padding(start = 5.dp)
//                    .size(width = 10.dp, height = 10.dp)
//
//            )
//            Text(
//                text = "4h ago",
//                style = MaterialTheme.typography.labelSmall,
//                color = colorResource(id = R.color.text_medium),
//                modifier = Modifier.padding(start = 5.dp)
//
//            )
//        }
    }
}


//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun PreviewCard(){
//    NewsAppComposeTheme {
//        NewsCardForDetails()
//    }
//}