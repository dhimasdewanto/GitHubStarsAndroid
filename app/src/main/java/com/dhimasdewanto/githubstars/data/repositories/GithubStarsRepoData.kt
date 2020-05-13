package com.dhimasdewanto.githubstars.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhimasdewanto.githubstars.core.*
import com.dhimasdewanto.githubstars.data.datasources.GithubStarsNetworkSource
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo

class GithubStarsRepoData(
    private val networkSource: GithubStarsNetworkSource
) : GithubStarsRepo {
    override suspend fun getListGithubStars(
        page: Int,
        searchQuery: String?
    ): Res<List<GitHubStars>, String> {
        return try {
            val listGithubStars = networkSource.getDataFromNetwork(page, searchQuery)
            Ok(listGithubStars)
        } catch (e: Throwable) {
            val message = "Connection Error: ${e.message}"
            Log.e("Connectivity", message, e)
            Err(message)
        }
    }


    private var currentPage: Int = 1
    private var searchQuery: String? = null

    private val listGithubStars = mutableListOf<GitHubStars>()
    private val _downloadedGithubStars = MutableLiveData<List<GitHubStars>>()
    override val downloadedGitHubStars: LiveData<List<GitHubStars>>
        get() = _downloadedGithubStars

    override suspend fun fetchGithubStars(searchText: String?) {
        currentPage = 1
        searchQuery = searchText
        try {
            val githubStars = networkSource.getDataFromNetwork(currentPage, searchQuery)
            listGithubStars.clear()
            listGithubStars.addAll(githubStars)
            _downloadedGithubStars.postValue(listGithubStars)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection.", e)
        }
    }

    override suspend fun loadMoreData() {
        currentPage++
        try {
            val githubStars = networkSource.getDataFromNetwork(currentPage, searchQuery)
            listGithubStars.addAll(githubStars)
            _downloadedGithubStars.postValue(listGithubStars)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection.", e)
        }
    }
}