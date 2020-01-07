package io.github.messiaslima.codewars.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.messiaslima.codewars.databinding.ListItemUserBinding
import io.github.messiaslima.codewars.entity.User

class UsersAdapter(
    private val viewModel: UsersViewModel
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val users =  mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder.from(parent)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val selectedUser = users[position]
        holder.bindUser(selectedUser)
        holder.itemView.setOnClickListener {
            viewModel.onUserSelected(selectedUser)
        }
    }

    fun updateUsers(newUsers: List<User>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    class UsersViewHolder(private val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindUser(user: User) {
            binding.user = user
        }

        companion object {
            fun from(parent: ViewGroup): UsersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
                return UsersViewHolder(binding)
            }
        }
    }
}