package com.dhimasdewanto.githubstars.view.screens.main.view_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.mvi.ScopeFragment
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.view.adapters.GitHubStarsRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_view_search.*
import kotlinx.android.synthetic.main.list_view_github_stars.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ViewSearchFragment : ScopeFragment<ViewSearchState, ViewSearchIntent>(), KodeinAware,
    GitHubStarsRecyclerAdapter.GitHubStarsViewHolder.Interaction {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ViewSearchViewModelFactory by instance()

    private lateinit var viewModel: ViewSearchViewModel
    private lateinit var recyclerAdapter: GitHubStarsRecyclerAdapter
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewSearchViewModel::class.java)
        setViewModelState(viewModel.viewModelState)
        setIntentChannel(viewModel.intentChannel)

        initRecyclerView()
        setOnSearchButton()
        setInfiniteScroll()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    /**
     * Go to detail page.
     */
    override fun onItemSelected(position: Int, item: GitHubStars) {
        val bundle = bundleOf("github_stars" to item)
        navController.navigate(R.id.action_nav_search_to_detailActivity, bundle)
    }

    private fun setOnSearchButton() {
        btn_search.setOnClickListener {
            val searchText = edit_text_search.text.toString()
            sendIntent(ViewSearchIntent.StartSearching(searchText))
        }
    }

    private fun initRecyclerView() {
        this.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = GitHubStarsRecyclerAdapter(this@ViewSearchFragment)
        recycler_view_github_stars.apply {
            layoutManager = this@ViewSearchFragment.layoutManager
            adapter = recyclerAdapter
        }
    }

    private fun setInfiniteScroll() {
        recycler_view_github_stars.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    loadWhenReachBottom()
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun loadWhenReachBottom() {
        val visibleItemCount = layoutManager.childCount
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val total = recyclerAdapter.itemCount

        if ((visibleItemCount + pastVisibleItem) >= total) {
            sendIntent(ViewSearchIntent.LoadMoreData)
        }
    }

    override fun handleState(state: ViewSearchState) {
        when(state) {
            is ViewSearchState.Initial -> {
                loading_bar.visibility = View.GONE
                text_search_initial.visibility = View.VISIBLE
            }
            is ViewSearchState.ShowResult -> {
                loading_bar.visibility = View.GONE
                text_search_initial.visibility = View.GONE
                recyclerAdapter.submitList(state.listGithubStars)
                recyclerAdapter.notifyDataSetChanged()
            }
            is ViewSearchState.Loading -> {
                loading_bar.visibility = View.VISIBLE
                text_search_initial.visibility = View.GONE
                recyclerAdapter.submitList(emptyList())
                recyclerAdapter.notifyDataSetChanged()
            }
            is ViewSearchState.LoadingMoreData -> {
                loading_bar.visibility = View.VISIBLE
                text_search_initial.visibility = View.GONE
            }
            is ViewSearchState.Error -> {
                loading_bar.visibility = View.GONE
                text_search_initial.visibility = View.GONE
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}
