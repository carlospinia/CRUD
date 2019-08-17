package com.example.crud.userList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import com.example.crud.toDateFormatted
import com.example.domain.User
import kotlinx.android.synthetic.main.user_list_item_lay.view.*
import java.text.SimpleDateFormat
import java.util.*

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

                val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault())
                tv_birthdate.text = user.birthdate?.toDateFormatted(sdf)

                when (user.id?.rem(10)){
                    0, 5 -> { iv_user.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_face_green)) }
                    1, 6 -> { iv_user.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_face_yellow)) }
                    2, 7 -> { iv_user.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_face_blue)) }
                    3, 8 -> { iv_user.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_face_orange)) }
                    4, 9 -> { iv_user.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_face_purple)) }
                }
            }
        }
    }

    fun setData(newUsers: List<User>){
        users = newUsers
        notifyDataSetChanged()
    }

}