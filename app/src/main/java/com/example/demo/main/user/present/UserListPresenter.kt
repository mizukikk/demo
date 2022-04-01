package com.example.demo.main.user.present

import com.example.demo.main.user.UserListContract
import com.example.demo.repository.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import productflavorsample.SchedulersProvider

class UserListPresenter(
    private var view: UserListContract.View?,
    private val githubRepository: GithubRepository
) : UserListContract.Presenter {

    companion object {
        private const val PRE_PAGE = 20
        private const val MAXIMUM_ITEM_COUNT = 100
    }

    private val compositeDisposable = CompositeDisposable()
    private var currentLastId = 0
    private var isLoadNext = false

    override fun loadFirstList() {
        view?.showProgress()
        val disposable = githubRepository.getGithubUserList(0, PRE_PAGE)
            .subscribeOn(SchedulersProvider.io())
            .observeOn(SchedulersProvider.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    view?.postUserList(it.body()!!)
                }
                view?.dismissProgress()
            }, { e ->
                e.printStackTrace()
                view?.dismissProgress()
            })
        compositeDisposable.add(disposable)
    }

    @Synchronized
    override fun loadNextList(lastId: Int, itemCount: Int) {
        if (itemCount >= MAXIMUM_ITEM_COUNT)
            return
        if (lastId != currentLastId && isLoadNext.not()) {
            isLoadNext = true
            val disposable = githubRepository.getGithubUserList(lastId, PRE_PAGE)
                .subscribeOn(SchedulersProvider.io())
                .observeOn(SchedulersProvider.mainThread())
                .subscribe({
                    if (it.isSuccessful) {
                        view?.postUserList(it.body()!!)
                    }
                    isLoadNext = false
                }, {
                    isLoadNext = false
                })
            compositeDisposable.add(disposable)
        }
    }

    override fun refreshList(itemCount: Int) {
        val disposable = githubRepository.getGithubUserList(0, itemCount)
            .subscribeOn(SchedulersProvider.io())
            .observeOn(SchedulersProvider.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    view?.postUserList(it.body()!!)
                }
                isLoadNext = false
            }, {
                isLoadNext = false
            })
        compositeDisposable.add(disposable)
    }

    override fun clear() {
        view = null
        compositeDisposable.clear()
    }

}