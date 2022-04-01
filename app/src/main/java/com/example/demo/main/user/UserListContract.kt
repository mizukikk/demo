package com.example.demo.main.user

import com.example.demo.api.obj.GithubUser

interface UserListContract {
    interface View {
        fun postUserList(list: List<GithubUser>)
        fun newUserList(list: List<GithubUser>)
        fun showProgress()
        fun dismissProgress()
    }

    interface Presenter {
        fun loadFirstList()
        fun loadNextList(lastId: Int, itemCount: Int)
        fun refreshList(itemCount: Int)
        fun clear()
    }
}