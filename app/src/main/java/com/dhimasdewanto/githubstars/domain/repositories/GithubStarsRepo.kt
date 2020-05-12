package com.dhimasdewanto.githubstars.domain.repositories

import androidx.lifecycle.LiveData
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

interface GithubStarsRepo {
    val downloadedGitHubStars : LiveData<List<GitHubStars>>

    suspend fun fetchGithubStars(searchText: String? = null)
    suspend fun loadMoreData()
}