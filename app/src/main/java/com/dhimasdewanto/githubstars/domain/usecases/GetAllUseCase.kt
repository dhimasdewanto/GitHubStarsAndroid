package com.dhimasdewanto.githubstars.domain.usecases

import androidx.lifecycle.LiveData
import com.dhimasdewanto.githubstars.core.UseCase
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllUseCase(
    private val repo: GithubStarsRepo
) : UseCase<LiveData<List<GitHubStars>>, GetAllUseCaseParams>  {
    override suspend fun call(params: GetAllUseCaseParams): LiveData<List<GitHubStars>> {
        val listData = repo.getGithubStars(params.page)
        return getIOContext(listData)
    }

    suspend fun loadMoreData() {
        repo.loadMoreData()
    }
}

data class GetAllUseCaseParams(
    val page: Int
)