package com.dhimasdewanto.githubstars.data.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhimasdewanto.githubstars.core.ITEMS_PER_PAGE
import com.dhimasdewanto.githubstars.core.NoConnectivityException
import com.dhimasdewanto.githubstars.data.services.GithubStarsApiService
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars

class GithubStarsNetworkSourceImpl(
    private val githubStarsApiService: GithubStarsApiService
) : GithubStarsNetworkSource {
    private var starPage: Int = 1
    private var lastQuery: String? = null

    private val listGithubStars = mutableListOf<GitHubStars>()
    private val _downloadedGithubStars = MutableLiveData<List<GitHubStars>>()
    override val downloadedGitHubStars: LiveData<List<GitHubStars>>
        get() = _downloadedGithubStars

    /**
     * [page] useless for now.
     */
    override suspend fun fetchGithubStars(page: Int, searchQuery: String?) {
        starPage = 1
        lastQuery = searchQuery
        try {
            val githubStars = getDataFromNetwork(starPage, lastQuery)
            listGithubStars.clear()
            listGithubStars.addAll(githubStars)
            _downloadedGithubStars.postValue(listGithubStars)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection.", e)
        }
    }

    override suspend fun loadMoreData() {
        starPage++
        try {
            val githubStars = getDataFromNetwork(starPage, lastQuery)
            listGithubStars.addAll(githubStars)
            _downloadedGithubStars.postValue(listGithubStars)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection.", e)
        }
    }

    private suspend fun getDataFromNetwork(page: Int, searchQuery: String?): List<GitHubStars> {
        val query = searchQuery ?: "stars:>1"

        val githubStarsFromApi =
            githubStarsApiService.getGithubStars(query, "stars", page, ITEMS_PER_PAGE)

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