package com.dhimasdewanto.githubstars.view.view_search

import androidx.lifecycle.ViewModel
import com.dhimasdewanto.githubstars.core.lazyDeferred
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class ViewSearchViewModel(
    private val githubStarsRepo: GithubStarsRepo
) : ViewModel() {
    val listGithubStars by lazyDeferred {
        githubStarsRepo.downloadedGitHubStars
    }

    suspend fun fetchGithubStars(searchText: String) {
        githubStarsRepo.fetchGithubStars(searchText)
    }
}
