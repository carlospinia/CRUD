package com.example.crud.userList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Failure
import com.example.crud.R
import com.example.crud.base.BaseFragment
import com.example.crud.navigateTo
import com.example.crud.userDetail.DetailScrenType
import com.example.crud.userDetail.UserDetailFragment
import com.example.domain.User
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserListFragment : BaseFragment<UserListVM>() {

    override fun getLayout() = R.layout.user_list_fragment
    override fun getViewModel() = UserListVM::class
    override val showToolbar = false

    companion object {
        fun setArguments() = bundleOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_users.adapter = UserListAdapter()
        rv_users.layoutManager = LinearLayoutManager(context)

        setToolbarTitle("")

        initObservers()
        initListeners()

        swipe_to_refresh.setOnRefreshListener { viewModel.loadUserList() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserList()
    }

    private fun initObservers(){
        viewModel.getUserList.observe(this, userListObs)
        viewModel.getError.observe(this, errorObs)
    }

    private fun initListeners() {
        fab_add.setOnClickListener {
            navigateTo(R.id.action_user_list_screen_to_user_detail_screen,
                UserDetailFragment.setArguments(DetailScrenType.ADD_USER))
        }
    }

    private val userListObs = Observer<List<User>> { newUserList ->
        swipe_to_refresh.isRefreshing = false
        (rv_users.adapter as UserListAdapter).setData(newUserList)
    }

    private val errorObs = Observer<Failure> { failure ->
        swipe_to_refresh.isRefreshing = false
        when (failure){
            is Failure.NetworkConnection -> showNetworkConnectionError()
            is Failure.ServerError -> showServerError()
        }
    }
}