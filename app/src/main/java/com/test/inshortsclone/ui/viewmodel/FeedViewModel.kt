package com.test.inshortsclone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.inshortsclone.data.entities.CategoryFeedData
import com.test.inshortsclone.data.repository.FeedRepository
import com.test.inshortsclone.ui.navigation.screens.BookmarkScreenUIState
import com.test.inshortsclone.ui.navigation.screens.FeedHomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FeedViewModel
@Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _feedState = MutableStateFlow<FeedHomeScreenUIState>(FeedHomeScreenUIState.START)
    private val _bookmarkState =
        MutableStateFlow<BookmarkScreenUIState>(BookmarkScreenUIState.START)
    private val _isBookmarkState = MutableStateFlow<Boolean>(true)
    val feedState: StateFlow<FeedHomeScreenUIState> get() = _feedState
    val bookmarkState: StateFlow<BookmarkScreenUIState> get() = _bookmarkState
    val isBookmarkState: StateFlow<Boolean> get() = _isBookmarkState

    init {
        getFeeds()
        getBookmarks()
    }

    fun getFeeds(category: String = "all") = viewModelScope.launch {
        _feedState.value = FeedHomeScreenUIState.LOADING

        try {
            _feedState.value =
                FeedHomeScreenUIState.SUCCESS(feedRepository.getListOfFeedForCategory(category = category))
        } catch (e: Exception) {
            _feedState.value = e.localizedMessage?.let { FeedHomeScreenUIState.FAILURE(it) }!!
        }

    }

     fun getBookmarks() = viewModelScope.launch {
        try {
            feedRepository.getListOfBookmark().collect() { bookmarks ->
                if (bookmarks.isEmpty()) {
                    _bookmarkState.value = BookmarkScreenUIState.START
                } else {
                    _bookmarkState.value = BookmarkScreenUIState.SUCCESS(bookmarks)
                }
            }
        } catch (e: Exception) {
            _bookmarkState.value = e.localizedMessage?.let { BookmarkScreenUIState.FAILURE(it) }!!
        }
    }

    fun isBookmarked(feed: String) = viewModelScope.launch() {
        _isBookmarkState.value = feedRepository.isBookmarked(feed)
    }


    fun addBookmark(feed: CategoryFeedData) = viewModelScope.launch {
        feedRepository.addBookmark(feed)
        getBookmarks()
    }

    fun deleteBookmark(feed: CategoryFeedData) = viewModelScope.launch {
        feedRepository.deleteBookmark(feed)
        getBookmarks()
    }
}