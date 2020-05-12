package com.dhimasdewanto.githubstars

import android.app.Application
import com.dhimasdewanto.githubstars.core.interceptors.ConnectivityInterceptor
import com.dhimasdewanto.githubstars.core.interceptors.ConnectivityInterceptorImpl
import com.dhimasdewanto.githubstars.data.datasources.GithubStarsNetworkSource
import com.dhimasdewanto.githubstars.data.datasources.GithubStarsNetworkSourceImpl
import com.dhimasdewanto.githubstars.data.repositories.GithubStarsRepoData
import com.dhimasdewanto.githubstars.data.services.GithubStarsApiService
import com.dhimasdewanto.githubstars.domain.repositories.GithubStarsRepo
import com.dhimasdewanto.githubstars.view.view_all.ViewAllViewModelFactory
import com.dhimasdewanto.githubstars.view.view_search.ViewSearchViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class GitHubStarsKodein : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@GitHubStarsKodein))

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { GithubStarsApiService(instance()) }
        bind<GithubStarsNetworkSource>() with singleton { GithubStarsNetworkSourceImpl(instance()) }
        bind<GithubStarsRepo>() with singleton { GithubStarsRepoData(instance()) }
        bind() from provider { ViewAllViewModelFactory(instance()) }
        bind() from provider { ViewSearchViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}