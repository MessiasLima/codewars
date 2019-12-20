package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUsersBinding
import io.github.messiaslima.codewars.ui.shared.BaseFragment
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : BaseFragment(), SearchUserDialogFragment.OnSearchUserListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        fragmentUsersBinding.viewModel = UsersViewModel()
        return fragmentUsersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuListener()
    }

    private fun setupMenuListener() {
        usersToolbar.setOnMenuItemClickListener { itemClicked ->

            if (itemClicked.itemId == R.id.menu_item_users_search) {
                showSearchToolbar()
                return@setOnMenuItemClickListener true
            }

            return@setOnMenuItemClickListener false
        }
    }

    override fun onSearchUser(username: String) {
    }

    private fun showSearchToolbar() {
        fragmentManager?.let {
            SearchUserDialogFragment.newInstance(this)
                .show(it, "fragment_search_user")
        }
    }
}
