package com.example.crud.userDetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.crud.CRUDActivity
import com.example.crud.R
import com.example.crud.base.BaseFragment
import kotlinx.android.synthetic.main.user_detail.*
import kotlin.reflect.KClass

class UserDetailFragment : BaseFragment<UserDetailVM>() {

    override fun getLayout() = R.layout.user_detail
    override fun getViewModel(): KClass<UserDetailVM> = UserDetailVM::class
    override val showToolbar = true

    companion object {
        private const val SCREEN_TYPE = "SCREEN_TYPE"

        fun setArguments(screenType: DetailScrenType) = bundleOf(
            SCREEN_TYPE to screenType
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        when(arguments?.get(SCREEN_TYPE)){
            DetailScrenType.USER_DETAIL -> setUserDetailUI()
            DetailScrenType.ADD_USER -> setAddUserUI()
            else -> setUserDetailUI()
        }
        setAddUserUI()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle( getString(R.string.user_detail_toolbar_title))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_detail_menu, menu)
    }

    private fun setAddUserUI(){
        setHasOptionsMenu(false)
    }

    private fun setUserDetailUI(){

    }

    private fun setEditUserUI(){
        setHasOptionsMenu(false)
    }
}