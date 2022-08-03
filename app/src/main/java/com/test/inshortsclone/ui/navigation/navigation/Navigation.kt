package com.test.inshortsclone.ui.navigation.navigation

sealed class Screen(val route : String ){
    object FeedScreen : Screen("feed_screen")
    object BookmarkScreen : Screen("bookmark_screen")
}

