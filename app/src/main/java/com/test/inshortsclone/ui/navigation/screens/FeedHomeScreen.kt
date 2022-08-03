package com.test.inshortsclone.ui.navigation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.test.inshortsclone.R
import com.test.inshortsclone.data.entities.CategoryFeedData
import com.test.inshortsclone.data.entities.FeedResponse
import com.test.inshortsclone.ui.viewmodel.FeedViewModel
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedState by viewModel.feedState.collectAsState()
    Column() {
        CategoryDropDown(viewModel)
        when (feedState) {
            FeedHomeScreenUIState.LOADING -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is FeedHomeScreenUIState.START -> {}
            is FeedHomeScreenUIState.SUCCESS -> {
                val feeds = (feedState as FeedHomeScreenUIState.SUCCESS).feed
                Column() {
                    LazyColumn() {
                        items(feeds.data) { feed ->
                            FeedItem(feed,viewModel)
                        }
                    }
                }
            }
            is FeedHomeScreenUIState.FAILURE -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Something went wrong...", fontSize = 16.sp)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryDropDown(viewModel: FeedViewModel) {
    val options = listOf(
        "all",
        "national",//Indian News only
        "business",
        "sports",
        "world",
        "politics",
        "technology",
        "startup",
        "entertainment",
        "miscellaneous",
        "hatke",
        "science",
        "automobile"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        expanded = expanded,

        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Select category") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        viewModel.getFeeds(selectedOptionText)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}


@Composable
fun FeedItem(feed: CategoryFeedData, viewModel: FeedViewModel) {
    Card(elevation = 4.dp, modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {

        Column(modifier = Modifier.padding(10.dp)) {

            Row() {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = rememberAsyncImagePainter(feed.imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Text(text = feed.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = feed.content)
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                /*IconButton(onClick = OnBookmarkButtonClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_bookmark_add_24),
                        contentDescription = "add Bookmark",
                        tint = MaterialTheme.colors.secondary
                    )
                }*/
                BookmarkButton(feed = feed, viewModel = viewModel)
            }
        }
    }
}
@Composable
fun BookmarkButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63),
    feed : CategoryFeedData,
    viewModel: FeedViewModel
) {
    val context = LocalContext.current
    viewModel.isBookmarked(feed.id)
    var isBookmarked by remember { mutableStateOf( viewModel.isBookmarkState.value) }

    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = {
            if (isBookmarked){
                viewModel.deleteBookmark(feed = feed)
                Toast.makeText(context,"Bookmark removed",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addBookmark(feed = feed)
                Toast.makeText(context,"Bookmark added",Toast.LENGTH_SHORT).show()
            }
            isBookmarked = !isBookmarked
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            painter = if (isBookmarked) {
                painterResource(id = R.drawable.ic_baseline_bookmark_remove_24)
            } else {
                painterResource(id = R.drawable.ic_baseline_bookmark_add_24)
            },
            contentDescription = null
        )
    }

}


sealed class FeedHomeScreenUIState() {
    object START : FeedHomeScreenUIState()
    object LOADING : FeedHomeScreenUIState()
    data class SUCCESS(val feed: FeedResponse) : FeedHomeScreenUIState()
    data class FAILURE(val message: String) : FeedHomeScreenUIState()

}
