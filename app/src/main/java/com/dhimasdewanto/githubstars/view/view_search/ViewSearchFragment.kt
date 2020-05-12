package com.dhimasdewanto.githubstars.view.view_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.ScopeFragment
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.view.view_all.ViewAllViewModelFactory
import com.dhimasdewanto.githubstars.view.view_all.adapters.GitHubStarsRecyclerAdapter
import kotlinx.android.synthetic.main.list_view_github_stars.*
import kotlinx.android.synthetic.main.view_search_fragment.*
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ViewSearchFragment : ScopeFragment(), KodeinAware,
    GitHubStarsRecyclerAdapter.GitHubStarsViewHolder.Interaction {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ViewSearchViewModelFactory by instance<ViewSearchViewModelFactory>()

    private lateinit var viewModel: ViewSearchViewModel
    private lateinit var recyclerAdapter: GitHubStarsRecyclerAdapter
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewSearchViewModel::class.java)
        initRecyclerView()
        onSearchAction()
    }

    /**
     * Go to detail page.
     */
    override fun onItemSelected(position: Int, item: GitHubStars) {
        val bundle = bundleOf("github_stars" to item)
        navController.navigate(R.id.action_viewAllFragment_to_viewDetailFragment, bundle)
    }

    private fun onSearchAction() {
        btn_search.setOnClickListener {
            val searchText = edit_text_search.text.toString()
            bindSearchResultToUI(searchText)
        }
    }

    private fun bindSearchResultToUI(searchText: String) = launch {
        viewModel.fetchGithubStars(searchText)
        val listGithubStars = viewModel.listGithubStars.await()
        listGithubStars.observe(viewLifecycleOwner, Observer { list ->
            recyclerAdapter.submitList(list)
        })
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        this.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = GitHubStarsRecyclerAdapter(this@ViewSearchFragment)
        recycler_view_github_stars.apply {
            layoutManager = this@ViewSearchFragment.layoutManager
            adapter = recyclerAdapter
        }
    }

}
