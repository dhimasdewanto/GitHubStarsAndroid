package com.dhimasdewanto.githubstars.domain.repositories

import androidx.lifecycle.LiveData
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

interface GithubStarsRepo {
    suspend fun getAndFetchGithubStars() : LiveData<List<GitHubStars>>
    suspend fun loadMoreData()

    suspend fun fetchGithubStars(searchText: String)
    suspend fun getGithubStars() : LiveData<List<GitHubStars>>
}