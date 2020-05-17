package com.dhimasdewanto.githubstars.view.main.view_search

import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

sealed class ViewSearchState {
    object Initial : ViewSearchState()

    data class ShowResult(
        val listGithubStars: List<GitHubStars>,
        val search: String,
        val page: Int
    ) : ViewSearchState()

    object Loading : ViewSearchState()

    object LoadingMoreData : ViewSearchState()

    data class Error(
        val message: String
    ) : ViewSearchState()
}