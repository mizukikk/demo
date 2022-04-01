package com.example.demo.repository

import com.example.demo.api.obj.GithubUser
import com.example.demo.api.obj.GithubUserDetail
import com.example.demo.repository.remote.GithubRemoteDataSource
import com.example.demo.repository.remote.IGithubRemoteDataSource
import io.reactivex.Single
import retrofit2.Response

class GithubRepository(
    private val githubDataSource: GithubRemoteDataSource
) : IGithubRemoteDataSource {

    companion object {
        private var INSTANCE: GithubRepository? = null
        fun getInstance(githubDataSource: GithubRemoteDataSource) =
            INSTANCE ?: synchronized(GithubRepository::class.java) {
                INSTANCE = GithubRepository(githubDataSource)
                INSTANCE
            }
    }

    override fun getGithubUserList(since: Int, perPage: Int): Single<Response<List<GithubUser>>> {
        return githubDataSource.getGithubUserList(since,perPage)
    }

    override fun getGithubUserDetail(login: String): Single<Response<GithubUserDetail>> {
        return githubDataSource.getGithubUserDetail(login)
    }
}