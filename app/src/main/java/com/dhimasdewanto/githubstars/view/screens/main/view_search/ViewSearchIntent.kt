package com.dhimasdewanto.githubstars.view.screens.main.view_search

import com.dhimasdewanto.githubstars.core.mvi.IIntent

sealed class ViewSearchIntent : IIntent {
    object LoadMoreData : ViewSearchIntent()
    data class StartSearching(val searchText: String) : ViewSearchIntent()
}