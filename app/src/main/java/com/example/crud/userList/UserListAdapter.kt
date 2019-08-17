package com.example.crud.userList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import com.example.domain.User
import kotlinx.android.synthetic.main.user_list_item_lay.view.*

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private var users = listOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_list_item_lay, parent, false)
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            with(itemView){
                tv_name.text = user.name
                tv_birthdate.text = user.birthdate
            }
        }
    }

    fun setData(newUsers: List<User>){
        users = newUsers
        notifyDataSetChanged()
    }

}