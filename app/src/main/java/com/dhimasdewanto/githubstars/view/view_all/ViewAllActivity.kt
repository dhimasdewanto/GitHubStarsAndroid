package com.dhimasdewanto.githubstars.view.view_all

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.ScopeActivity
import com.dhimasdewanto.githubstars.core.lazyDeferred
import com.dhimasdewanto.githubstars.domain.usecases.GetAllUseCase
import com.dhimasdewanto.githubstars.domain.usecases.GetAllUseCaseParams
import com.dhimasdewanto.githubstars.view.view_all.adapters.GitHubStarsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_view_all.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class ViewAllActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
    }
}
