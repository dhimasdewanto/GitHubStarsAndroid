package com.dhimasdewanto.githubstars.view.view_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import kotlinx.android.synthetic.main.activity_view_detail.*

class ViewDetailActivity : AppCompatActivity() {

    private lateinit var githubStars: GitHubStars

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_detail)

        val value = intent.extras?.get("github_stars") as GitHubStars

        if (value == null) {
            text_detail.text = "ERROR!"
        } else {
            text_detail.text = value.name
        }
    }
}
