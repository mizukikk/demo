package com.example.demo.main

import com.example.demo.main.user.data.UserDetailArgs

interface MainInteractivity {
    fun showProgress()
    fun dismissProgress()
    fun setUserDetailFragment(args: UserDetailArgs)
}