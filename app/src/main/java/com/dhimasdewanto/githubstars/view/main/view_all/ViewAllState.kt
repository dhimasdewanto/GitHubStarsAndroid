package com.dhimasdewanto.githubstars.view.main.view_all

import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

sealed class ViewAllState {
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