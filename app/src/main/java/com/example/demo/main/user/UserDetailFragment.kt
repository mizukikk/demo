package com.example.demo.main.user

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.demo.R
import com.example.demo.api.obj.GithubUserDetail
import com.example.demo.databinding.FragmentUserDetailBinding
import com.example.demo.main.MainInteractivity
import com.example.demo.main.user.data.UserDetailArgs
import com.example.demo.main.user.present.UserDetailPresenter
import com.example.demo.ui.base.BaseFragment
import org.koin.android.ext.android.get

class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>(R.layout.fragment_user_detail),
    UserDetailContract.View {

    companion object {
        fun newInstance(args: UserDetailArgs) = UserDetailFragment().apply {
            arguments =
                Bundle().apply {
                    putParcelable(UserDetailFragment::class.java.simpleName, args)
                }
        }
    }

    private var parentActivity: MainInteractivity? = null
    private lateinit var present: UserDetailContract.Presenter

    override fun bindView(view: View) = FragmentUserDetailBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPresenter()
    }

    private fun initPresenter() {
        present = UserDetailPresenter(this, get())
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

    override fun postUserDetailData(detail: GithubUserDetail) {

    }

    override fun showProgress() {
        parentActivity?.showProgress()
    }

    override fun dismissProgress() {
        parentActivity?.dismissProgress()
    }
}