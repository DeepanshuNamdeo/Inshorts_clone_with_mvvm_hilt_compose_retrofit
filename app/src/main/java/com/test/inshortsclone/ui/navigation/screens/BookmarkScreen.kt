package com.test.inshortsclone.ui.navigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.inshortsclone.data.entities.CategoryFeedData
import com.test.inshortsclone.ui.viewmodel.FeedViewModel

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val bookmarkState by viewModel.bookmarkState.collectAsState()
    when (bookmarkState) {
        BookmarkScreenUIState.LOADING -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is BookmarkScreenUIState.START -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No Bookmarks")
            }
        }
        is BookmarkScreenUIState.SUCCESS -> {
            val feeds = (bookmarkState as BookmarkScreenUIState.SUCCESS).feed

            Column() {
                LazyColumn() {
                    items(feeds) { feed ->
                        FeedItem(feed,viewModel)
                    }
                }
            }
        }
        is BookmarkScreenUIState.FAILURE -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "Something went wrong...", fontSize = 16.sp)
            }
        }
    }

}

sealed class BookmarkScreenUIState() {
    object START : BookmarkScreenUIState()
    object LOADING : BookmarkScreenUIState()
    data class SUCCESS(val feed: MutableList<CategoryFeedData>) : BookmarkScreenUIState()
    data class FAILURE(val message: String) : BookmarkScreenUIState()

}