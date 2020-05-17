package com.dhimasdewanto.githubstars.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.setupActionBarWithNavController
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var githubStars: GitHubStars

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setGithubStarsData()
        bindUI()
        setActionBarTitle()
    }

    private fun setGithubStarsData() {
        val stars = intent.getParcelableExtra<GitHubStars>("github_stars")
        githubStars = stars!!
    }

    private fun bindUI() {
        text_detail_title.text = "${githubStars.ownerName}/${githubStars.name}"
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = githubStars.name
    }

}
