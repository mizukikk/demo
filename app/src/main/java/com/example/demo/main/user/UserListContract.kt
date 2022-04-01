package com.example.demo.main.user

interface UserListContract {
    interface View {
        fun showProgress()
        fun dismissProgress()
    }

    interface Presenter {
        fun loadFirstList()
        fun loadNextList()
        fun refreshList()
        fun clear()
    }
}