package com.example.demo.main

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.main.user.UserDetailFragment
import com.example.demo.main.user.UserListFragment
import com.example.demo.main.user.data.UserDetailArgs

class MainActivity : AppCompatActivity(), MainInteractivity {

    private val progressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UserListFragment().beginTransaction()
    }

    fun Fragment.beginTransaction() {
        if (this.isAdded.not()) {
            supportFragmentManager.beginTransaction()
                .let {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
                    if (currentFragment != null) {
                        it.remove(currentFragment)
                    }
                    it.add(R.id.container, this)
                        .commit()
                }
        } else {
            supportFragmentManager.beginTransaction()
                .let {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
                    if (currentFragment != null)
                        it.remove(currentFragment)
                    it.show(this)
                        .commit()
                }
        }
    }

    fun Fragment.beginTransactionStack() {
        if (this.isAdded.not()) {
            supportFragmentManager.beginTransaction().let { transition ->
                val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
                currentFragment?.let {
                    transition.hide(currentFragment)
                }
                transition.add(R.id.container, this)
                    .addToBackStack(this::class.java.simpleName)
                    .commit()
            }
        }
    }

    override fun showProgress() {
        if (progressDialog.isShowing.not()) {
            progressDialog.show()
        }
    }

    override fun dismissProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun setUserDetailFragment(args: UserDetailArgs) {
        UserDetailFragment.newInstance(args).beginTransactionStack()
    }
}