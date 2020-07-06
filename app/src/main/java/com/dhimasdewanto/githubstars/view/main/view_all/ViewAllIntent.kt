package com.dhimasdewanto.githubstars.view.main.view_all

import com.dhimasdewanto.githubstars.core.mvi.IIntent

sealed class ViewAllIntent : IIntent {
    object StartFetching : ViewAllIntent()
    object LoadMoreData : ViewAllIntent()
}