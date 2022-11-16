package com.example.crud.userDetail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.crud.*
import com.example.crud.databinding.UserDetailBinding
import com.example.domain.UserFailure
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _binding: UserDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: UserDetailVM by viewModels()

    private var loading: Boolean by LoadingDelegate()

    private var menu: Menu? = null

    companion object {
        private const val SCREEN_TYPE = "SCREEN_TYPE"

        fun setArguments(screenType: DetailScrenType) = bundleOf(SCREEN_TYPE to screenType,)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setUiState(arguments?.get(SCREEN_TYPE) as DetailScrenType)
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, loadingObs)
        viewModel.getCreateUserSuccess.observe(viewLifecycleOwner, createUserSuccessObs)
        viewModel.getEditUserSuccess.observe(viewLifecycleOwner, editUserSuccessObs)
        viewModel.getGetUserSuccess.observe(viewLifecycleOwner, getUserSuccessObs)
        viewModel.getDeleteUserSuccess.observe(viewLifecycleOwner, getDeleteSuccessObs)
        viewModel.getError.observe(viewLifecycleOwner, errorObs)
        viewModel.getUndoList.observe(viewLifecycleOwner, undoListObs)
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                when(uiState){
                    is DetailScrenType.UserDetail -> setUserDetailUI(uiState.user)
                    is DetailScrenType.EditUser -> setUserDetailUI(uiState.user)
                    DetailScrenType.AddUser -> setAddUserUI()
                }
            }
        }
    }

    private val loadingObs = Observer<Boolean>{ showLoading ->
        loading = showLoading
    }

    private val createUserSuccessObs = Observer<Boolean> {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    private val editUserSuccessObs = Observer<Boolean> {
        Toast.makeText(context, getString(R.string.edit_user_success), Toast.LENGTH_LONG).show()
        viewModel.getUser()
    }

    private val getUserSuccessObs = Observer<UserApp> { user ->
        binding.etUsername.setText(user?.name)
        binding.etBirthdate.setText(user?.birthdate?.datePrettyFormat())
    }

    private val getDeleteSuccessObs = Observer<Boolean> {
        Toast.makeText(context, getString(R.string.delete_user_success), Toast.LENGTH_LONG).show()
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    private val errorObs = Observer<UserFailure> { failure ->
        when (failure) {
            is UserFailure.NetworkConnection -> showNetworkConnectionError()
            is UserFailure.ServerError -> showServerError()
            UserFailure.InvalidDateFormat -> showInvalidDateFormatError()
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

    private fun initListeners() = with(binding) {

        with(toolbarDetail){
            icBack.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }

            menuSave.setOnClickListener {
                viewModel.onSaveButtonClicked(etUsername.text.toString(), etBirthdate.text.toString())
            }

            menuDelete.setOnClickListener {
                showDeleteUserConfirm()
            }

            menuEdit.setOnClickListener {
                setEditUserUI()
            }

            menuUndo.setOnClickListener {
                viewModel.onUndoButtonClicked()
                etUsername.setText(viewModel.getUndoList.value?.last()?.name)
                etBirthdate.setText(viewModel.getUndoList.value?.last()?.birthdate?.datePrettyFormat())
            }
        }

        btnCancelEditUser.setOnClickListener {
            setUserDetailUI(viewModel.user)
        }

        etBirthdate.setOnClickListener {
            if (etBirthdate.text.isNullOrEmpty()) {
                showDatePicker()
            } else {
                showDatePicker(etBirthdate.text.toString().timeInMillisFromPretty())
            }
        }
    }

    private fun showDatePicker(timeInMillisFromPretty: Long = Calendar.getInstance().timeInMillis) {
        context?.let {
            val cal = Calendar.getInstance()
            cal.timeInMillis = timeInMillisFromPretty
            val dialog = DatePickerDialog(it, { view, _, _, _ ->
                binding.etBirthdate.setText(view.getDateInMillis().toDatePretty())
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }

    private fun setAddUserUI() = with(binding) {

        with(toolbarDetail){
            toolbarTitle.text = getString(R.string.add_user_toolbar_title)
            menuUndo.isVisible = false
            menuDelete.isVisible = false
            menuEdit.isVisible = false
            menuSave.isVisible = true
        }

        etUsername.isEnabled = true
        etBirthdate.isEnabled = true

        btnCancelEditUser.isVisible = false
    }

    private fun setUserDetailUI(user: UserApp?) = with(binding) {
        with(toolbarDetail){
            toolbarTitle.text = getString(R.string.user_detail_toolbar_title)
            menuUndo.isVisible = false
            menuDelete.isVisible = true
            menuEdit.isVisible = true
            menuSave.isVisible = false
        }

        etUsername.apply {
            isEnabled = false
            setText(user?.name.toString())
        }

        etBirthdate.apply {
            isEnabled = false
            setText(user?.birthdate.toString().datePrettyFormat())
        }

        btnCancelEditUser.isVisible = false
    }

    private fun setEditUserUI() = with(binding) {

        with(toolbarDetail){
            toolbarTitle.text = getString(R.string.edit_user_toolbar_title)
            menuUndo.isVisible = true
            menuDelete.isVisible = false
            menuEdit.isVisible = false
            menuSave.isVisible = true
        }

        etUsername.isEnabled = true
        etBirthdate.isEnabled = true
        btnCancelEditUser.isVisible = true
    }

    private fun showDeleteUserConfirm() {
        val dialog = AlertDialog.Builder(context)
        dialog.setPositiveButton(getString(R.string.accept)) { d, _ ->
            viewModel.onDeleteButtonClicked()
            d.dismiss()
        }.setNegativeButton(getString(R.string.cancel)) { d, _ ->
            d.dismiss()
        }.setMessage(getString(R.string.delete_user_confirm_message))
        dialog.show()
    }

    private fun showInvalidDateFormatError(){
        Toast.makeText(context, getString(R.string.invalid_date_format), Toast.LENGTH_LONG).show()
    }
}