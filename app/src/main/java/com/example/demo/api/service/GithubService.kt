package com.example.demo.api.service

import com.example.demo.api.obj.GithubUser
import com.example.demo.api.obj.GithubUserDetail
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users")
    fun getGithubUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Single<Response<List<GithubUser>>>

    @GET("users/{login}")
    fun getGithubUserDetail(@Path("login") login: String): Single<Response<GithubUserDetail>>
}