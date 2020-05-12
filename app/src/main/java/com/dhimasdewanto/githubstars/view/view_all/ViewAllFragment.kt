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

class ViewAllFragment : ScopeFragment(), KodeinAware,
    GitHubStarsRecyclerAdapter.GitHubStarsViewHolder.Interaction {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ViewAllViewModelFactory by instance<ViewAllViewModelFactory>()

    private lateinit var viewModel: ViewAllViewModel
    private lateinit var recyclerAdapter: GitHubStarsRecyclerAdapter
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager

    private var isLoading: Boolean = false

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
        bindUI()
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

    private fun bindUI() = launch {
        val listGithubStars = viewModel.listGithubStars.await()
        listGithubStars.observe(viewLifecycleOwner, Observer { list ->
            recyclerAdapter.submitList(list)
        })
    }

    private fun initRecyclerView() {
        this.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = GitHubStarsRecyclerAdapter(this@ViewAllFragment)
        recycler_view_github_stars.apply {
            layoutManager = this@ViewAllFragment.layoutManager
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
            if (!isLoading) {
                setLoading(true)
                loadMoreData()
            }
        }
    }

    private fun loadMoreData() = launch {
        viewModel.loadMoreData()
        recyclerAdapter.notifyDataSetChanged()
        setLoading(false)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            this.isLoading = true
            loading_bar.visibility = View.VISIBLE
            return
        }

        this.isLoading = false
        loading_bar.visibility = View.GONE
    }

    private fun goToSearch() {
        btn_float_search.setOnClickListener {
            navController.navigate(R.id.action_viewAllFragment_to_viewSearchFragment)
        }
    }
}
