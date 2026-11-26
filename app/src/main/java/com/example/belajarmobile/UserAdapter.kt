package com.example.belajarmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvNamaUser: TextView =
            itemView.findViewById(R.id.tvNamaUser)

        val tvEmailUser: TextView =
            itemView.findViewById(R.id.tvEmailUser)

        val tvUsernameUser: TextView =
            itemView.findViewById(R.id.tvUsernameUser)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {

        val user = users[position]

        holder.tvNamaUser.text = user.name
        holder.tvEmailUser.text = user.email
        holder.tvUsernameUser.text = "@${user.username}"
    }

    override fun getItemCount(): Int {
        return users.size
    }
}