package com.example.demo.main.user.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.demo.api.obj.GithubUser

class GithubUserDiffCall(
    private val nList: List<GithubUser>,
    private val oList: List<GithubUser>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oList.size

    override fun getNewListSize() = nList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return nList[newItemPosition].id == oList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return nList[newItemPosition] == oList[oldItemPosition]
    }
}