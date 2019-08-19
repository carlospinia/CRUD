package com.example.crud.userDetail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.core.Failure
import com.example.crud.*
import com.example.crud.base.BaseFragment
import kotlinx.android.synthetic.main.user_detail.*
import java.util.*
import kotlin.reflect.KClass

class UserDetailFragment : BaseFragment<UserDetailVM>() {

    override fun getLayout() = R.layout.user_detail
    override fun getViewModel(): KClass<UserDetailVM> = UserDetailVM::class
    override val showToolbar = true

    private var menu: Menu? = null

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

        initObservers()
        initListeners()
    }

    override fun onResume() {
        super.onResume()

        when(arguments?.get(SCREEN_TYPE)){
            DetailScrenType.USER_DETAIL -> {
                viewModel.user = arguments?.get(USER) as UserApp
                viewModel.initUndoList()
                setUserDetailUI(viewModel.user)
            }
            DetailScrenType.ADD_USER -> setAddUserUI()
            else -> setUserDetailUI(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        inflater.inflate(R.menu.user_detail_menu, this.menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_delete -> showDeleteUserConfirm()
            R.id.menu_edit -> setEditUserUI()
            R.id.menu_undo -> {
                viewModel.removeLastUndoItem()
                et_username.setText(viewModel.getUndoList.value?.last()?.name)
                et_birthdate.setText(viewModel.getUndoList.value?.last()?.birthdate?.datePrettyFormat())
            }
        }
        return true
    }

    private fun hideOptionMenu(@IdRes id: Int){
        menu?.findItem(id)?.isVisible = false
    }

    private fun showOptionMenu(@IdRes id: Int){
        menu?.findItem(id)?.isVisible = true
    }

    private fun initObservers(){
        viewModel.getCreateUserSuccess.observe(this, createUserSuccessObs)
        viewModel.getEditUserSuccess.observe(this, editUserSuccessObs)
        viewModel.getGetUserSuccess.observe(this, getUserSuccessObs)
        viewModel.getDeleteUserSuccess.observe(this, getDeleteSuccessObs)
        viewModel.getError.observe(this, errorObs)

        viewModel.getUndoList.observe(this, undoListObs)
    }

    private val createUserSuccessObs = Observer<Boolean>{
        activity?.onBackPressed()
    }

    private val editUserSuccessObs = Observer<Boolean> {
        Toast.makeText(context, getString(R.string.edit_user_success), Toast.LENGTH_LONG).show()
        viewModel.getUser()
    }

    private val getUserSuccessObs = Observer<UserApp>{ user ->
        et_username.setText(user?.name)
        et_birthdate.setText(user?.birthdate?.datePrettyFormat())
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

    private val undoListObs = Observer<MutableList<UserApp>> { undoList ->
        val itemUndo = menu?.findItem(R.id.menu_undo)
        itemUndo?.let {
            it.isEnabled = undoList.size > 1
            if (it.isEnabled) {
                it.setIcon(R.drawable.ic_undo)
            } else {
                it.setIcon(R.drawable.ic_undo_disabled)
            }
        }
    }

    private fun initListeners(){
        btn_add_user.setOnClickListener {
            viewModel.createUser(et_username.text.toString(), et_birthdate.text.toString())
        }

        btn_cancel_edit_user.setOnClickListener {
            setUserDetailUI(viewModel.user)
        }

        btn_edit_user.setOnClickListener{
            viewModel.editUser(et_username.text.toString(), et_birthdate.text.toString())
        }

        et_birthdate.setOnClickListener {
            if (et_birthdate.text.isNullOrEmpty()){
                showDatePicker()
            } else {
                showDatePicker(et_birthdate.text.toString().timeInMillisFromPretty())
            }
        }
    }

    private fun showDatePicker(timeInMillisFromPretty: Long = Calendar.getInstance().timeInMillis) {
        context?.let{
            val cal = Calendar.getInstance()
            cal.timeInMillis = timeInMillisFromPretty
            val dialog = DatePickerDialog(it, DatePickerDialog.OnDateSetListener { view, _, _, _ ->
                et_birthdate.setText(view.getDateInMillis().toDatePretty())
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }

    private fun setAddUserUI(){
        setToolbarTitle( getString(R.string.add_user_toolbar_title))
        setHasOptionsMenu(false)
        hideOptionMenu(R.id.menu_undo)
        hideOptionMenu(R.id.menu_delete)
        hideOptionMenu(R.id.menu_edit)

        et_username.isEnabled = true
        et_birthdate.isEnabled = true

        btn_add_user.visibility = View.VISIBLE
        btn_edit_user.visibility = View.GONE
        btn_cancel_edit_user.visibility = View.GONE
    }

    private fun setUserDetailUI(user: UserApp?){
        setToolbarTitle( getString(R.string.user_detail_toolbar_title))

        setHasOptionsMenu(true)
        hideOptionMenu(R.id.menu_undo)
        showOptionMenu(R.id.menu_delete)
        showOptionMenu(R.id.menu_edit)

        btn_add_user.visibility = View.GONE

        et_username.apply {
            isEnabled = false
            setText(user?.name.toString())
        }

        et_birthdate.apply {
            isEnabled = false
            setText(user?.birthdate.toString().datePrettyFormat())
        }

        btn_add_user.visibility = View.GONE
        btn_edit_user.visibility = View.GONE
        btn_cancel_edit_user.visibility = View.GONE
    }

    private fun setEditUserUI(){
        setHasOptionsMenu(true)
        showOptionMenu(R.id.menu_undo)
        hideOptionMenu(R.id.menu_delete)
        hideOptionMenu(R.id.menu_edit)

        setToolbarTitle( getString(R.string.edit_user_toolbar_title))

        et_username.isEnabled = true
        et_birthdate.isEnabled = true

        btn_add_user.visibility = View.GONE
        btn_edit_user.visibility = View.VISIBLE
        btn_cancel_edit_user.visibility = View.VISIBLE
    }

    private fun showDeleteUserConfirm(){
        val dialog = AlertDialog.Builder(context)
        dialog.setPositiveButton(getString(R.string.accept)){ d, _ ->
            viewModel.deleteUser()
            d.dismiss()
        }.setNegativeButton(getString(R.string.cancel)){ d, _ ->
                d.dismiss()
        }.setMessage(getString(R.string.delete_user_confirm_message))
        dialog.show()
    }

}