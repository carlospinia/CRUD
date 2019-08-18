package com.example.crud.userDetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.core.Failure
import com.example.crud.R
import com.example.crud.UserApp
import com.example.crud.base.BaseFragment
import com.example.crud.timeInMillis
import kotlinx.android.synthetic.main.user_detail.*
import kotlin.reflect.KClass

class UserDetailFragment : BaseFragment<UserDetailVM>() {

    override fun getLayout() = R.layout.user_detail
    override fun getViewModel(): KClass<UserDetailVM> = UserDetailVM::class
    override val showToolbar = true

    private lateinit var user: UserApp

    companion object {
        private const val SCREEN_TYPE = "SCREEN_TYPE"
        private const val USER = "USER"

        fun setArguments(screenType: DetailScrenType, user: UserApp? = null) = bundleOf(
            SCREEN_TYPE to screenType,
            USER to user
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(arguments?.get(SCREEN_TYPE)){
            DetailScrenType.USER_DETAIL -> {
                user = arguments?.get(USER) as UserApp
                setUserDetailUI(user)
            }
            DetailScrenType.ADD_USER -> setAddUserUI()
            else -> setUserDetailUI(null)
        }

        initObservers()
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            showDeleteUserConfirm()
        } else if (item.itemId == R.id.menu_edit){
            setEditUserUI()
        }
        return true
    }

    private fun initObservers(){
        viewModel.getCreateUserSuccess.observe(this, createUserSuccessObs)
        viewModel.getEditUserSuccess.observe(this, editUserSuccessObs)
        viewModel.getGetUserSuccess.observe(this, getUserSuccessObs)
        viewModel.getDeleteUserSuccess.observe(this, getDeleteSuccessObs)
        viewModel.getError.observe(this, errorObs)
    }

    private val createUserSuccessObs = Observer<Boolean>{
        activity?.onBackPressed()
    }

    private val editUserSuccessObs = Observer<Boolean> {
        Toast.makeText(context, getString(R.string.edit_user_success), Toast.LENGTH_LONG).show()
        viewModel.getUser(user.id ?: 0)
    }

    private val getUserSuccessObs = Observer<UserApp>{ user ->
        setUserDetailUI(user)
    }

    private val getDeleteSuccessObs = Observer<Boolean>{
        Toast.makeText(context, getString(R.string.delete_user_success), Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    private val errorObs = Observer<Failure> { failure ->
        when (failure){
            is Failure.NetworkConnection -> showNetworkConnectionError()
            is Failure.ServerError -> showServerError()
        }
    }

    private fun initListeners(){
        btn_add_user.setOnClickListener {
            viewModel.createUser(et_username.text.toString(), calendar_view.date)
        }

        btn_cancel_edit_user.setOnClickListener {
            setUserDetailUI(user)
        }

        btn_edit_user.setOnClickListener{
            viewModel.editUser(et_username.text.toString(), calendar_view.date, user.id ?: 0)
        }
    }

    private fun setAddUserUI(){
        setToolbarTitle( getString(R.string.add_user_toolbar_title))
        setHasOptionsMenu(false)
        overlap_calendar.visibility = View.GONE
        btn_add_user.visibility = View.VISIBLE
        btn_edit_user.visibility = View.GONE
        btn_cancel_edit_user.visibility = View.GONE
    }

    private fun setUserDetailUI(user: UserApp?){
        setToolbarTitle( getString(R.string.user_detail_toolbar_title))
        setHasOptionsMenu(true)
        btn_add_user.visibility = View.GONE

        et_username.apply {
            isEnabled = false
            setText(user?.name.toString())
        }

        overlap_calendar.visibility = View.VISIBLE
        btn_add_user.visibility = View.GONE
        btn_edit_user.visibility = View.GONE
        btn_cancel_edit_user.visibility = View.GONE

        user?.let { u ->
            u.birthdate?.let { b ->
                calendar_view.date = b.timeInMillis()
            }
        }
    }

    private fun setEditUserUI(){
        setHasOptionsMenu(false)
        setToolbarTitle( getString(R.string.edit_user_toolbar_title))
        overlap_calendar.visibility = View.GONE
        btn_add_user.visibility = View.GONE
        btn_edit_user.visibility = View.VISIBLE
        btn_cancel_edit_user.visibility = View.VISIBLE
        et_username.apply {
            isEnabled = true
        }
    }

    private fun showDeleteUserConfirm(){
        val dialog = AlertDialog.Builder(context)
        dialog.setPositiveButton(getString(R.string.accept)){ d, _ ->
            viewModel.deleteUser(user.id ?: 0)
            d.dismiss()
        }.setNegativeButton(getString(R.string.cancel)){ d, _ ->
                d.dismiss()
        }.setMessage(getString(R.string.delete_user_confirm_message))
        dialog.show()
    }

}