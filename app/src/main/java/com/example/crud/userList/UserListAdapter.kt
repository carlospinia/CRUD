package com.example.crud.userList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import com.example.crud.UserApp
import com.example.crud.databinding.UserListItemLayBinding
import com.example.crud.datePrettyFormat

class UserListAdapter(
    private val userSelected: (UserApp) -> Unit
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private var users = listOf<UserApp>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        UserListItemLayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ViewHolder(
        private val itemBinding: UserListItemLayBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(user: UserApp) = with(itemBinding) {
            root.setOnClickListener { userSelected(user) }

            tvName.text = user.name

            tvBirthdate.text = user.birthdate?.datePrettyFormat()

            when (user.id?.rem(10)) {
                0, 5 -> ivUser.setAvatar(R.drawable.ic_face_green)
                1, 6 -> ivUser.setAvatar(R.drawable.ic_face_yellow)
                2, 7 -> ivUser.setAvatar(R.drawable.ic_face_blue)
                3, 8 -> ivUser.setAvatar(R.drawable.ic_face_orange)
                else -> ivUser.setAvatar(R.drawable.ic_face_purple)
            }
        }

        private fun AppCompatImageView.setAvatar(@DrawableRes avatar: Int) {
            setImageDrawable(ContextCompat.getDrawable(context, avatar))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newUsers: List<UserApp>) {
        users = newUsers
        notifyDataSetChanged()
    }

}