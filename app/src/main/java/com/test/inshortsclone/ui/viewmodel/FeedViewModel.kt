package com.test.inshortsclone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.inshortsclone.data.repository.FeedRepository
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
    val feedState: StateFlow<FeedHomeScreenUIState> get() = _feedState

    init {
        getFeeds()
    }

    public fun getFeeds(category: String = "all") = viewModelScope.launch {
        _feedState.value = FeedHomeScreenUIState.LOADING

        try {
            _feedState.value =
                FeedHomeScreenUIState.SUCCESS(feedRepository.getListOfFeedForCategory(category = category))
        } catch (e: Exception) {
            _feedState.value = e.localizedMessage?.let { FeedHomeScreenUIState.FAILURE(it) }!!
        }

    }
}