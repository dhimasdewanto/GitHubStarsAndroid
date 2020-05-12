package com.dhimasdewanto.githubstars.data.datasources

import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

interface GithubStarsNetworkSource {
    suspend fun getDataFromNetwork(page: Int, searchQuery: String? = null): List<GitHubStars>
}