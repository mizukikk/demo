package com.example.demo.main.user

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import androidx.appcompat.app.AlertDialog
import coil.load
import coil.transform.CircleCropTransformation
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
    private lateinit var args: UserDetailArgs

    override fun bindView(view: View) = FragmentUserDetailBinding.bind(view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            args = it.getParcelable(UserDetailFragment::class.java.simpleName)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPresenter()
        initView()
        setListener()
        present.loadDetailData(args.login)
    }

    private fun setListener() {
        binding.ivClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.tvBlog.setOnClickListener {
            val uri = try {
                val url = binding.tvBlog.text.toString()
                val valid = URLUtil.isValidUrl(url)
                if (valid)
                    Uri.parse(url)
                else
                    null
            } catch (e: NullPointerException) {
                null
            }
            uri?.let {
                val intent = Intent(Intent.ACTION_VIEW, it)
                startActivity(intent)
            }
        }
    }

    private fun initView() {
        binding.tvBlog.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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
        binding.ivAvatar.load(detail.avatarUrl) {
            placeholder(R.drawable.ic_default_avatar)
            error(R.drawable.ic_default_avatar)
            transformations(CircleCropTransformation())
        }
        binding.tvName.text = detail.name
        binding.tvDesc.text = detail.bio
        binding.tvLogin.text = detail.login
        binding.tvStaffState.visibility = if (detail.siteAdmin) View.VISIBLE else View.GONE
        binding.tvLocation.text = detail.location
        binding.tvBlog.text = detail.blog
    }

    override fun showProgress() {
        parentActivity?.showProgress()
    }

    override fun dismissProgress() {
        parentActivity?.dismissProgress()
    }

    override fun showLoadErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.dialog_message_service_error))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.button_ok)) { d, w ->
                parentFragmentManager.popBackStack()
            }
            .show()
    }
}