package com.example.demo.main.user.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailArgs(
    val login: String
) : Parcelable