package com.dhimasdewanto.githubstars.domain.usecases

import androidx.lifecycle.LiveData
import com.dhimasdewanto.githubstars.core.UseCase
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class SearchUseCase(
    private val repo: GithubStarsRepo
) : UseCase<LiveData<List<GitHubStars>>, SearchUseCaseParams> {
    override suspend fun call(params: SearchUseCaseParams): LiveData<List<GitHubStars>> {
        val listData = repo.searchGithubStars(params.searchText, params.page)
        return getIOContext(listData)
    }
}

data class SearchUseCaseParams(
    val searchText: String,
    val page: Int
)