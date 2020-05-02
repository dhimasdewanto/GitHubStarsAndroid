package com.dhimasdewanto.githubstars.view.view_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.ScopeFragment
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import kotlinx.android.synthetic.main.view_detail_fragment.*

class ViewDetailFragment : ScopeFragment() {
    private lateinit var viewModel: ViewDetailViewModel
    private lateinit var githubStars: GitHubStars

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stars = requireArguments().getParcelable<GitHubStars>("github_stars")
        githubStars = stars!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_detail_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewDetailViewModel::class.java)

        text_title.text = "${githubStars.ownerName}/${githubStars.name}"
        text_description.text = githubStars.description
    }

}
