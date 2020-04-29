package com.dhimasdewanto.githubstars.domain.repositories

import androidx.lifecycle.LiveData
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

interface GithubStarsRepo {
    suspend fun getGithubStars(page: Int) : LiveData<List<GitHubStars>>
}