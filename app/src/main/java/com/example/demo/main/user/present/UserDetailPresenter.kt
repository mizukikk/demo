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

    override fun loadDetailData(login: String) {
        view?.showProgress()
        val disposable = githubRepository.getGithubUserDetail(login)
            .subscribeOn(SchedulersProvider.io())
            .observeOn(SchedulersProvider.mainThread())
            .subscribe({ result ->
                if (result.isSuccessful) {
                    view?.postUserDetailData(result.body()!!)
                    view?.dismissProgress()
                } else {
                    view?.dismissProgress()
                    view?.showLoadErrorDialog()
                }
            }, { e ->
                view?.dismissProgress()
                view?.showLoadErrorDialog()
            })
        compositeDisposable.add(disposable)
    }

    override fun clear() {
        view = null
        compositeDisposable.clear()
    }

}