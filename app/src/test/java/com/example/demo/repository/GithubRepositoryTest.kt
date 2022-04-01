package com.example.demo.repository

import com.example.demo.api.MockInterceptor
import com.example.demo.api.MockResponse
import com.example.demo.api.NetworkService
import com.example.demo.repository.remote.GithubRemoteDataSource
import junit.framework.TestCase
import org.junit.Assert

class GithubRepositoryTest : TestCase() {

    private lateinit var mockInterceptor:MockInterceptor
    private lateinit var networkService : NetworkService
    private lateinit var githubRemoteDataSource : GithubRemoteDataSource
    private lateinit var githubRepository : GithubRepository

    override fun setUp() {
        mockInterceptor = MockInterceptor()
        networkService = NetworkService(mockInterceptor)
        githubRemoteDataSource = GithubRemoteDataSource(networkService.githubService)
        githubRepository = GithubRepository(githubRemoteDataSource)
    }

    fun testGetGithubUserList() {

        mockInterceptor.setListener(object : MockInterceptor.MockInterceptorListener {
            override fun setApiResponse(url: String): MockResponse {
                val status = 200;
                val response = Utils.readText("api/fakeUserList.json")
                return MockResponse(status, response)
            }
        })

        val result = githubRepository
            .getGithubUserList()
            .blockingGet()

        Assert.assertTrue(result.isSuccessful)

        val response = result.body()

        Assert.assertNotNull(response)
        Assert.assertEquals(1, response!!.size)

        val user = response[0]

        Assert.assertEquals("mojombo", user.login)
        Assert.assertEquals("https://avatars.githubusercontent.com/u/1?v=4", user.avatarUrl)
        Assert.assertEquals(false, user.siteAdmin)

    }

    fun testGetGithubUserDetail() {
        mockInterceptor.setListener(object : MockInterceptor.MockInterceptorListener {
            override fun setApiResponse(url: String): MockResponse {
                val status = 200;
                val response = Utils.readText("fakeUserDetail.json")
                return MockResponse(status, response)
            }
        })

        val result = githubRepository
            .getGithubUserDetail()
            .blockingGet()

        Assert.assertTrue(result.isSuccessful)

        val response = result.body()

        Assert.assertNotNull(response)

        Assert.assertEquals("https://avatars.githubusercontent.com/u/1?v=4", response!!.avatarUrl)
        Assert.assertEquals("Tom Preston-Werner", response.name)
        Assert.assertEquals(null, response.bio)
        Assert.assertEquals("mojombo", response.login)
        Assert.assertEquals(false, response.siteAdmin)
        Assert.assertEquals("San Francisco", response.location)
        Assert.assertEquals("http://tom.preston-werner.com", response.blog)
    }
}