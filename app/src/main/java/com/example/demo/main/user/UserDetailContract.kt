package com.example.demo.main.user

import com.example.demo.api.obj.GithubUser
import com.example.demo.api.obj.GithubUserDetail

interface UserDetailContract {
    interface View {
        fun postUserDetailData(detail: GithubUserDetail)
        fun showProgress()
        fun dismissProgress()
        fun showLoadErrorDialog()
    }

    interface Presenter {
        fun loadDetailData(login: String)
        fun clear()
    }
}