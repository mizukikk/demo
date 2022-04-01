package com.example.demo.main.user.present

import com.example.demo.api.obj.GithubUserDetail
import com.example.demo.main.user.UserDetailContract
import com.example.demo.main.user.UserListContract
import com.example.demo.repository.GithubRepository
import com.example.demo.repository.remote.GithubRemoteDataSource
import com.example.demo.repository.remote.IGithubRemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import junit.framework.TestCase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class UserDetailPresenterTest : TestCase() {

    @MockK(relaxed = true)
    private lateinit var view: UserDetailContract.View

    @MockK(relaxed = true)
    private lateinit var githubRemoteDataSource: IGithubRemoteDataSource

    private lateinit var presenter: UserDetailPresenter

    private lateinit var githubRepository: GithubRepository

    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        githubRepository = GithubRepository(githubRemoteDataSource)
        presenter = UserDetailPresenter(view, githubRepository)
    }

    fun testLoadDetailDataSuccess() {

        val response = Gson().fromJson(Utils.readText("api/fakeUserDetail.json"),GithubUserDetail::class.java)

        val login = "mojombo"

        every { githubRemoteDataSource.getGithubUserDetail(eq(login)) }
            .answers {
                Single.just(Response.success(response))
            }

        presenter.loadDetailData(login)

        verify {
            view.postUserDetailData(eq(response))
        }

    }

    fun testLoadDetailDataFail() {

        val login = "mojombo"

        every { githubRemoteDataSource.getGithubUserDetail(eq(login)) }
            .answers {
                Single.just(Response.error(500,"erroe message".toResponseBody("text/json".toMediaTypeOrNull())))
            }

        presenter.loadDetailData(login)

        verify {
            view.showLoadErrorDialog()
        }

    }
}