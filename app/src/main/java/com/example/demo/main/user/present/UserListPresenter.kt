package com.example.demo.main.user.present

import com.example.demo.main.user.UserListContract
import com.example.demo.repository.GithubRepository

class UserListPresenter(
    private var view: UserListContract.View?,
    private val repository: GithubRepository
) : UserListContract.Presenter {

    override fun loadFirstList() {
        TODO("Not yet implemented")
    }

    override fun loadNextList() {
        TODO("Not yet implemented")
    }

    override fun refreshList() {
        TODO("Not yet implemented")
    }

    override fun clear() {
        view = null
    }

}