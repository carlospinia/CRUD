package com.example.crud.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud.*
import com.example.crud.databinding.UserListFragmentBinding
import com.example.crud.userDetail.DetailScrenType
import com.example.crud.userDetail.UserDetailFragment
import com.example.domain.UserListFailure
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var _binding: UserListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: UserListVM by viewModels()

    private var loading: Boolean by LoadingDelegate()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsers.adapter = UserListAdapter { user ->
            navigateTo(
                R.id.user_detail_screen,
                UserDetailFragment.setArguments(DetailScrenType.UserDetail(user))
            )
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(context)

        initObservers()
        initListeners()

        binding.swipeToRefresh.setOnRefreshListener { viewModel.loadUserList() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserList()
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, loadingObs)
        viewModel.getUserList.observe(viewLifecycleOwner, userListObs)
        viewModel.getError.observe(viewLifecycleOwner, errorObs)
    }

    private fun initListeners() = with(binding) {
        fabAdd.setOnClickListener {
            navigateTo(
                R.id.action_user_list_screen_to_user_detail_screen,
                UserDetailFragment.setArguments(DetailScrenType.AddUser)
            )
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (rvUsers.adapter as UserListAdapter).setData(viewModel.filterUsers(newText ?: ""))
                return true
            }
        })
    }

    private val userListObs = Observer<List<UserApp>> { newUserList ->
        binding.swipeToRefresh.isRefreshing = false
        (binding.rvUsers.adapter as UserListAdapter).setData(newUserList)
    }

    private val errorObs = Observer<UserListFailure> { failure ->
        binding.swipeToRefresh.isRefreshing = false
        when (failure) {
            is UserListFailure.NetworkConnection -> showNetworkConnectionError()
            is UserListFailure.ServerError -> showServerError()
        }
    }

    private val loadingObs = Observer<Boolean> { showLoading ->
        loading = showLoading
    }
}