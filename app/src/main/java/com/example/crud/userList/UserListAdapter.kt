package com.example.crud.userList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import kotlinx.android.synthetic.main.user_list_item_lay.view.*

class UserListAdapter(private val users: List<Pair<String, String>>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_list_item_lay, parent, false)
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: Pair<String, String>) {
            with(itemView){
                tv_name.text = user.first
                tv_birthdate.text = user.second
            }
        }
    }

}