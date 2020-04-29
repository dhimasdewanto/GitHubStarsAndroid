package com.dhimasdewanto.githubstars.data.services

import com.dhimasdewanto.githubstars.core.interceptors.ConnectivityInterceptor
import com.dhimasdewanto.githubstars.data.models.GithubStarsApiModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_ADDRESS = "https://api.github.com/search/"

interface GithubStarsApiService {
    @GET("repositories")
    suspend fun getGithubStars(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GithubStarsApiModel

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : GithubStarsApiService {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubStarsApiService::class.java)
        }
    }
}