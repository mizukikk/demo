package com.example.demo.repository.remote

import com.example.demo.api.obj.GithubUser
import com.example.demo.api.obj.GithubUserDetail
import io.reactivex.Single
import retrofit2.Response

interface IGithubRemoteDataSource {
    fun getGithubUserList(since: Int, perPage: Int): Single<Response<List<GithubUser>>>
    fun getGithubUserDetail(login: String): Single<Response<GithubUserDetail>>
}