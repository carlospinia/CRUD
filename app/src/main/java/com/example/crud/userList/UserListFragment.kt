package com.example.crud.userList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud.R
import com.example.crud.base.BaseFragment
import com.example.domain.User
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserListFragment : BaseFragment<UserListVM>() {

    override fun getLayout() = R.layout.user_list_fragment
    override fun getViewModel() = UserListVM::class

    companion object {
        fun createBundle() = bundleOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_users.adapter = UserListAdapter()
        rv_users.layoutManager = LinearLayoutManager(context)

        initObservers()

        viewModel.loadUserList()
    }

    private fun initObservers(){
        viewModel.getUserList.observe(this, userListObs)
    }

    private val userListObs = Observer<List<User>> {
        (rv_users.adapter as UserListAdapter).setData(it)
    }
}