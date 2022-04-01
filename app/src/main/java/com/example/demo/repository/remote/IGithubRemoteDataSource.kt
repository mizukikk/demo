package com.example.demo.repository.remote

import com.example.demo.api.obj.GithubUser
import io.reactivex.Single
import retrofit2.Response

interface IGithubRemoteDataSource {
    fun getGithubUserList(): Single<Response<List<GithubUser>>>
    fun getGithubUserDetail(): Single<Response<GithubUser>>
}