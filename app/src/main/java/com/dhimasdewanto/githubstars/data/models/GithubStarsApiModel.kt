package com.dhimasdewanto.githubstars.data.models


import com.google.gson.annotations.SerializedName

data class GithubStarsApiModel(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)