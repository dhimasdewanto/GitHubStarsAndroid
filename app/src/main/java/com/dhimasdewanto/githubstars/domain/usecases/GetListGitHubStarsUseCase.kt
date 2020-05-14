package com.dhimasdewanto.githubstars.domain.usecases

import com.dhimasdewanto.githubstars.core.*
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class GetListGitHubStarsUseCase(
    private val githubStarsRepo: GithubStarsRepo
) :
    UseCase<List<GitHubStars>, GetListGitHubStarsParams> {

    override suspend fun call(params: GetListGitHubStarsParams): Res<List<GitHubStars>, Failure> {
        if (validate(params) is Err) {
            return validate(params) as Err
        }

        return githubStarsRepo.getListGithubStars(params.page, params.searchQuery)
    }

    override suspend fun validate(params: GetListGitHubStarsParams): Res<Unit, Failure> {
        return Ok(Unit)
    }

}

data class GetListGitHubStarsParams(
    val page: Int,
    val searchQuery: String? = null
)