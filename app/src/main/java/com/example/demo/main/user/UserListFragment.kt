package com.example.demo.main.user

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.api.obj.GithubUser
import com.example.demo.databinding.FragmentUserListBinding
import com.example.demo.main.MainInteractivity
import com.example.demo.main.user.adapter.GithubUserAdapter
import com.example.demo.main.user.present.UserListPresenter
import com.example.demo.ui.base.BaseFragment
import org.koin.android.ext.android.get

class UserListFragment : BaseFragment<FragmentUserListBinding>(R.layout.fragment_user_list),
    UserListContract.View {

    private var parentActivity: MainInteractivity? = null
    private lateinit var present: UserListContract.Presenter
    private val githubUserAdapter by lazy { GithubUserAdapter() }

    override fun bindView(view: View) = FragmentUserListBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPresent()
        initView()
        binding.refreshUser.setOnRefreshListener {
            present.refreshList(githubUserAdapter.itemCount)
        }
        present.loadFirstList()
    }

    private fun initView() {
        binding.rvGithubUser.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun scrollVerticallyBy(
                dy: Int,
                recycler: RecyclerView.Recycler?,
                state: RecyclerView.State?
            ): Int {
                val realScrollY = super.scrollVerticallyBy(dy, recycler, state)
                if (realScrollY != dy) {
                    val overScroll = dy - realScrollY
                    if (overScroll > 0) {
                        present.loadNextList(githubUserAdapter.lastId, githubUserAdapter.itemCount)
                    }
                }
                return realScrollY
            }
        }
        binding.rvGithubUser.adapter = githubUserAdapter
    }

    private fun initPresent() {
        present = UserListPresenter(this, get())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInteractivity) {
            parentActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        present.clear()
    }

    override fun postUserList(list: List<GithubUser>) {
        if (binding.refreshUser.isRefreshing)
            binding.refreshUser.isRefreshing = false
        githubUserAdapter.swapData(list)
    }

    override fun newUserList(list: List<GithubUser>) {
        githubUserAdapter.addData(list)
    }

    override fun showProgress() {
        parentActivity?.showProgress()
    }

    override fun dismissProgress() {
        parentActivity?.dismissProgress()
    }
}