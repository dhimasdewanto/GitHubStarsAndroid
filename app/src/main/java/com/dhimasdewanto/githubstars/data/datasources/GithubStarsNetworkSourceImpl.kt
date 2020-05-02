package com.dhimasdewanto.githubstars.data.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhimasdewanto.githubstars.core.NoConnectivityException
import com.dhimasdewanto.githubstars.data.services.GithubStarsApiService
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

class GithubStarsNetworkSourceImpl(
    private val githubStarsApiService: GithubStarsApiService
) : GithubStarsNetworkSource {
    private val _downloadedGithubStars = MutableLiveData<List<GitHubStars>>()
    override val downloadedGitHubStars: LiveData<List<GitHubStars>>
        get() = _downloadedGithubStars

    override suspend fun fetchGithubStars(page: Int, perPage: Int, searchQuery: String?) {
        val query = searchQuery ?: "stars:>1"

        try {
            val githubStarsFromApi =
                githubStarsApiService.getGithubStars(query, "stars", page, perPage)
            val githubStars = githubStarsFromApi.items.map { stars ->
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
            _downloadedGithubStars.postValue(githubStars)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection.", e)
        }
    }
}