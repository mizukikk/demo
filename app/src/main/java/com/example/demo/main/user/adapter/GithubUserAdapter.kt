package com.example.demo.main.user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.demo.R
import com.example.demo.api.obj.GithubUser
import com.example.demo.databinding.ItemGithubUserBinding

class GithubUserAdapter : RecyclerView.Adapter<GithubUserAdapter.GithubUserHolder>() {

    private var githubUserList = listOf<GithubUser>()
    private var listener: GithubUserListener? = null
    val lastId
        get() = try {
            githubUserList.last().id
        } catch (e: NoSuchElementException) {
            -1
        }

    private var lastClickMillis = 0L
    private val enableClick
        get():Boolean {
            val currentMillis = System.currentTimeMillis()
            val diff = currentMillis - lastClickMillis
            val enable = diff >= 200
            if (enable)
                lastClickMillis = currentMillis
            return enable
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GithubUserHolder(ItemGithubUserBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: GithubUserHolder, position: Int) {
        val user = githubUserList[position]
        holder.bindData(user)
    }

    override fun getItemCount() = githubUserList.size

    fun swapData(githubUserList: List<GithubUser>) {
        val diffCall = GithubUserDiffCall(githubUserList, this.githubUserList)
        val result = DiffUtil.calculateDiff(diffCall)
        result.dispatchUpdatesTo(this)
        this.githubUserList = githubUserList
    }

    fun addData(newGithubUserList: List<GithubUser>) {
        val oldList = mutableListOf<GithubUser>()
        oldList.addAll(githubUserList)

        val newList = mutableListOf<GithubUser>()
        newList.addAll(oldList)
        newList.addAll(newGithubUserList)

        val diffCall = GithubUserDiffCall(newList, oldList)
        val result = DiffUtil.calculateDiff(diffCall)
        result.dispatchUpdatesTo(this)
        this.githubUserList = newList
    }

    fun setListener(listener: GithubUserListener) {
        this.listener = listener
    }

    interface GithubUserListener {
        fun openDetailPage(user: GithubUser)
    }

    inner class GithubUserHolder(private val binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(user: GithubUser) {
            setItemState(user)
            setItemDate(user)
            setListener(user)
        }

        private fun setListener(user: GithubUser) {
            binding.root.setOnClickListener {
                if (enableClick) {
                    listener?.openDetailPage(user)
                }
            }
        }

        private fun setItemState(user: GithubUser) {
            binding.tvStaff.visibility = if (user.siteAdmin) View.VISIBLE else View.GONE
        }

        private fun setItemDate(user: GithubUser) {
            binding.tvName.text = user.login
            binding.ivAvatar.load(user.avatarUrl) {
                placeholder(R.drawable.ic_default_avatar)
                error(R.drawable.ic_default_avatar)
                transformations(CircleCropTransformation())
            }
        }
    }
}