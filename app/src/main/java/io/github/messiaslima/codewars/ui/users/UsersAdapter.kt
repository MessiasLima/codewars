package io.github.messiaslima.codewars.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.entity.User
import kotlinx.android.synthetic.main.list_item_user.view.*

class UsersAdapter(
    private val onItemClickedCallback: ((user: User) -> Unit)? = null
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val users =  mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val selectedUser = users[position]
        holder.bindUser(selectedUser)
        holder.itemView.setOnClickListener {
            onItemClickedCallback?.invoke(selectedUser)
        }
    }

    fun updateUsers(newUsers: List<User>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    class UsersViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.listItemUserName
        private val username = itemView.listItemUserUsername

        fun bindUser(user: User) {
            name.text = user.name
            username.text = user.username
        }
    }
}