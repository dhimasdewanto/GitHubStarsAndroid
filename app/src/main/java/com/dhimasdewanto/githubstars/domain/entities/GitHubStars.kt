package com.dhimasdewanto.githubstars.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubStars(
    val id: Int,
    val name: String,
    val ownerName: String,
    val ownerImageUrl: String,
    val description: String,
    val starsCount: Int,
    val programmingLanguage: String,
    val license: String,
    val openIssues: Int
) : Parcelable