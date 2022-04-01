package com.example.demo.main.user.present

import com.example.demo.api.obj.GithubUser
import com.example.demo.main.user.UserListContract
import com.example.demo.repository.GithubRepository
import com.example.demo.repository.remote.GithubRemoteDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import junit.framework.TestCase
import retrofit2.Response

class UserListPresenterTest : TestCase() {

    @MockK(relaxed = true)
    private lateinit var view: UserListContract.View

    @MockK(relaxed = true)
    private lateinit var githubRemoteDataSource: GithubRemoteDataSource

    private lateinit var presenter: UserListPresenter
    private lateinit var githubRepository: GithubRepository

    override fun setUp() {
        MockKAnnotations.init(this)
        githubRepository = GithubRepository(githubRemoteDataSource)
        presenter = UserListPresenter(view, githubRepository)
    }

    fun testLoadFirstList() {
        val response = Gson().fromJson<List<GithubUser>>(
            Utils.readText("api/fakeUserList.json"),
            object : TypeToken<List<GithubUser>>() {}.type
        )

        val sine = 0
        val prePage = 20

        every { githubRemoteDataSource.getGithubUserList(eq(sine), eq(prePage)) }
            .answers {
                Single.just(Response.success(response))
            }

        presenter.loadFirstList()

        verify {
            view.postUserList(eq(response))
        }

    }

    fun testLoadNextList() {

        val response = Gson().fromJson<List<GithubUser>>(
            Utils.readText("api/fakeUserList.json"),
            object : TypeToken<List<GithubUser>>() {}.type
        )

        val sine = 20
        val prePage = 20

        every { githubRemoteDataSource.getGithubUserList(eq(sine), eq(prePage)) }
            .answers {
                Single.just(Response.success(response))
            }

        presenter.loadNextList(sine, prePage)

        verify {
            view.newUserList(eq(response))
        }

    }

    fun testRefreshList() {

        val response = Gson().fromJson<List<GithubUser>>(
            Utils.readText("api/fakeUserList.json"),
            object : TypeToken<List<GithubUser>>() {}.type
        )

        val sine = 0
        val prePage = 100

        every { githubRemoteDataSource.getGithubUserList(eq(sine), eq(prePage)) }
            .answers {
                Single.just(Response.success(response))
            }

        presenter.refreshList(100)

        verify {
            view.postUserList(eq(response))
        }

    }
}