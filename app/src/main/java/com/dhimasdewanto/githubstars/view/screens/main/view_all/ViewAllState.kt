package com.dhimasdewanto.githubstars.view.screens.main.view_all

import com.dhimasdewanto.githubstars.core.mvi.IState
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

sealed class ViewAllState : IState {
    object Initial : ViewAllState()

    data class ShowResult(
        val listGithubStars: List<GitHubStars>,
        val page: Int
    ) : ViewAllState()

    object LoadingMoreData : ViewAllState()

    data class Error(
        val message: String
    ) : ViewAllState()
}