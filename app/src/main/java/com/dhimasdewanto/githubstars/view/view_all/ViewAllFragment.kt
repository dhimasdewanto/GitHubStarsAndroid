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
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.ScopeFragment
import com.dhimasdewanto.githubstars.domain.entities.GitHubStars
import com.dhimasdewanto.githubstars.view.view_all.adapters.GitHubStarsRecyclerAdapter
import kotlinx.android.synthetic.main.view_all_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ViewAllFragment : ScopeFragment(), KodeinAware, GitHubStarsRecyclerAdapter.GitHubStarsViewHolder.Interaction {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ViewAllViewModelFactory by instance<ViewAllViewModelFactory>()

    private lateinit var viewModel: ViewAllViewModel
    private lateinit var recyclerAdapter: GitHubStarsRecyclerAdapter
    private lateinit var navController: NavController

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onItemSelected(position: Int, item: GitHubStars) {
        // TODO: Go to detail page.
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
        recycler_view_github_stars.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerAdapter = GitHubStarsRecyclerAdapter(this@ViewAllFragment)
            adapter = recyclerAdapter
        }
    }
}
