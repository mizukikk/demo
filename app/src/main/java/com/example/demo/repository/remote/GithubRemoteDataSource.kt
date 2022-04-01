package com.example.demo.repository.remote

import com.example.demo.api.obj.GithubUser
import com.example.demo.api.obj.GithubUserDetail
import com.example.demo.api.service.GithubService
import io.reactivex.Single
import retrofit2.Response

class GithubRemoteDataSource(
    private val githubService: GithubService
) : IGithubRemoteDataSource {
    override fun getGithubUserList(since: Int, perPage: Int): Single<Response<List<GithubUser>>> {
        return githubService.getGithubUserList(since, perPage)
    }

    override fun getGithubUserDetail(login: String): Single<Response<GithubUserDetail>> {
        return githubService.getGithubUserDetail(login)
    }
}