package com.dhimasdewanto.githubstars.data.datasources

import com.dhimasdewanto.githubstars.core.ITEMS_PER_PAGE
import com.dhimasdewanto.githubstars.data.models.GithubStarsApiModel
import com.dhimasdewanto.githubstars.data.services.GithubStarsApiService
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

class GithubStarsNetworkSourceImpl(
    private val githubStarsApiService: GithubStarsApiService
) : GithubStarsNetworkSource {
    override suspend fun getDataFromNetwork(page: Int, searchQuery: String?): List<GitHubStars> {
        val query = searchQuery ?: "stars:>1"

        val githubStarsFromApi =
            githubStarsApiService.getGithubStars(query, "stars", page, ITEMS_PER_PAGE)

        return convertModel(githubStarsFromApi)
    }

    private fun convertModel(githubStarsFromApi: GithubStarsApiModel): List<GitHubStars> {
        return githubStarsFromApi.items.map { stars ->
            var programmingLanguage: String? = stars.language
            if (programmingLanguage == null) {
                programmingLanguage = ""
            }
            GitHubStars(
                stars.id,
                stars.name,
                stars.owner.login,
                stars.owner.avatarUrl,
                stars.description,
                stars.stargazersCount,
                programmingLanguage,
                stars.description,
                stars.openIssues
            )
        }
    }
}