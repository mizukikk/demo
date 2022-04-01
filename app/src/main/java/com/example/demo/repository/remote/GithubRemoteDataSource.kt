package com.example.demo.repository.remote

import com.example.demo.api.obj.GithubUser
import com.example.demo.api.service.GithubService
import io.reactivex.Single
import retrofit2.Response

class GithubRemoteDataSource(
    private val githubService: GithubService
) :IGithubRemoteDataSource{
    override fun getGithubUserList(): Single<Response<List<GithubUser>>> {
        TODO("Not yet implemented")
    }

    override fun getGithubUserDetail(): Single<Response<GithubUser>> {
        TODO("Not yet implemented")
    }
}