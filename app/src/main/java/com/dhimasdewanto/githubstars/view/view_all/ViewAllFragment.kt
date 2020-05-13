package com.dhimasdewanto.githubstars.view.view_all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.Ok
import com.dhimasdewanto.githubstars.core.ScopeFragment
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.view.view_all.adapters.GitHubStarsRecyclerAdapter
import kotlinx.android.synthetic.main.list_view_github_stars.*
import kotlinx.android.synthetic.main.view_all_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Error

class ViewAllFragment : ScopeFragment(), KodeinAware,
    GitHubStarsRecyclerAdapter.GitHubStarsViewHolder.Interaction {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ViewAllViewModelFactory by instance<ViewAllViewModelFactory>()

    private lateinit var viewModel: ViewAllViewModel
    private lateinit var recyclerAdapter: GitHubStarsRecyclerAdapter
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_all_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewAllViewModel::class.java)
        initRecyclerView()
        bindListGithubStars()
        bindIsLoading()
        setInfiniteScroll()
        goToSearch()
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
        navController.navigate(R.id.action_viewAllFragment_to_viewDetailFragment, bundle)
    }

    private fun initRecyclerView() {
        this.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = GitHubStarsRecyclerAdapter(this@ViewAllFragment)
        recycler_view_github_stars.apply {
            layoutManager = this@ViewAllFragment.layoutManager
            adapter = recyclerAdapter
        }
    }

    private fun bindListGithubStars() = launch {
        viewModel.fetchGithubStars()
        val listGithubStars = viewModel.downloadedGitHubStars
        listGithubStars.observe(viewLifecycleOwner, Observer { list ->
            recyclerAdapter.submitList(list)
        })
    }

    private fun bindIsLoading() {
        val isLoading = viewModel.isLoading
        isLoading.observe(viewLifecycleOwner, Observer { isLoad ->
            when(isLoad) {
                true -> loading_bar.visibility = View.VISIBLE
                false -> loading_bar.visibility = View.GONE
            }
        })
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
            loadMoreData()
        }
    }

    private fun loadMoreData() = launch {
        when(viewModel.fetchGithubStars()) {
            is Ok -> recyclerAdapter.notifyDataSetChanged()
        }
    }

    private fun goToSearch() {
        btn_float_search.setOnClickListener {
            navController.navigate(R.id.action_viewAllFragment_to_viewSearchFragment)
        }
    }
}
