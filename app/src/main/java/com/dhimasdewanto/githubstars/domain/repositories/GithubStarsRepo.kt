package com.dhimasdewanto.githubstars.domain.repositories

import androidx.lifecycle.LiveData
import com.dhimasdewanto.githubstars.core.Res
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

interface GithubStarsRepo {
    suspend fun getListGithubStars(
        page: Int,
        searchQuery: String? = null
    ): Res<List<GitHubStars>, String>


    val downloadedGitHubStars: LiveData<List<GitHubStars>>

    suspend fun fetchGithubStars(searchText: String? = null)
    suspend fun loadMoreData()
}