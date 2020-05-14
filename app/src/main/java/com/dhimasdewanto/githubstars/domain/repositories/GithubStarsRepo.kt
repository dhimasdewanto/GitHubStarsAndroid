package com.dhimasdewanto.githubstars.domain.repositories

import com.dhimasdewanto.githubstars.core.Failure
import com.dhimasdewanto.githubstars.core.Res
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

interface GithubStarsRepo {
    suspend fun getListGithubStars(
        page: Int,
        searchQuery: String? = null
    ): Res<List<GitHubStars>, Failure>
}