package com.example.demo.main.user.present

import com.example.demo.main.user.UserDetailContract
import com.example.demo.main.user.UserListContract
import com.example.demo.repository.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import productflavorsample.SchedulersProvider

class UserDetailPresenter(
    private var view: UserDetailContract.View?,
    private val githubRepository: GithubRepository
) : UserDetailContract.Presenter {

    private val compositeDisposable = CompositeDisposable()


    override fun clear() {
        view = null
        compositeDisposable.clear()
    }

}