package com.example.demo.main.user

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.demo.R
import com.example.demo.databinding.FragmentUserListBinding
import com.example.demo.main.MainInteractivity
import com.example.demo.main.user.present.UserListPresenter
import com.example.demo.ui.base.BaseFragment
import org.koin.android.ext.android.get

class UserListFragment : BaseFragment<FragmentUserListBinding>(R.layout.fragment_user_list),
    UserListContract.View {

    private var parentActivity: MainInteractivity? = null
    private lateinit var present: UserListContract.Presenter

    override fun bindView(view: View) = FragmentUserListBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    override fun showProgress() {
        parentActivity?.showProgress()
    }

    override fun dismissProgress() {
        parentActivity?.dismissProgress()
    }
}