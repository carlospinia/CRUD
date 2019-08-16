package com.example.crud.userList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud.R
import com.example.crud.base.BaseFragment
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserListFragment : BaseFragment<UserListVM>() {

    override fun getLayout() = R.layout.user_list_fragment
    override fun getViewModel() = UserListVM::class

    companion object {
        fun createBundle() = bundleOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_users.adapter = UserListAdapter(listOf(
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995",
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995",
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995",
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995",
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995",
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995",
            "Carlos" to "22/06/1993",
            "Sara" to "15/12/1995"
        ))
        rv_users.layoutManager = LinearLayoutManager(context)
    }
}