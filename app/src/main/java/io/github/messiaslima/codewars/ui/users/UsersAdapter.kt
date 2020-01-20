package io.github.messiaslima.codewars.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.ListItemUserBinding
import io.github.messiaslima.codewars.entity.Rank
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

            val bestLanguage = getBestLanguage(user)

            binding.user = user
            binding.listItemUserName.text = getValidName(user)
            binding.listItemUserBestLanguage.text = binding.root.context.getString(
                R.string.best_language,
                bestLanguage.first,
                bestLanguage.second
            )
        }

        fun getValidName(user: User): String? {
            return if (user.name?.isNotBlank() == true) {
                user.name
            } else {
                user.username
            }
        }

        private fun getBestLanguage(user: User): Pair<String, Long> {

            val languages = user.ranks?.languages

            val sortedLanguages = languages?.toSortedMap(Comparator { o1, o2 ->
                val language1 = languages[o1]
                val language2 = languages[o2]
                return@Comparator  language1?.score?.compareTo(language2?.score ?: 0) ?: 0
            })


            return Pair(
                sortedLanguages!!.lastKey(),
                sortedLanguages[sortedLanguages.lastKey()]!!.score!!
            )
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