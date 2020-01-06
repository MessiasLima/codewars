package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.messiaslima.codewars.EventObserver
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUsersBinding
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.shared.navigateTo
import io.github.messiaslima.codewars.ui.shared.showErrorMessage
import io.github.messiaslima.codewars.ui.user.UserFragment

class UsersFragment : Fragment() {

    private lateinit var fragmentUsersBinding: FragmentUsersBinding
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        fragmentUsersBinding.lifecycleOwner = viewLifecycleOwner
        fragmentUsersBinding.viewModel = viewModel

        return fragmentUsersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuListener()
        setupUsersRecyclerView()
        setupErrorHandler()
        setupNavigationEvent()
    }

    private fun setupNavigationEvent() {
        viewModel.goToDetailsEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToDetails(it)
        })
    }

    private fun setupErrorHandler() {
        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            showErrorMessage("", it)
        })
    }

    private fun setupUsersRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        val usersAdapter = UsersAdapter(this::navigateToDetails)

        val usersRecyclerView = fragmentUsersBinding.usersRecyclerView
        usersRecyclerView.adapter = usersAdapter
        usersRecyclerView.layoutManager = layoutManager
        usersRecyclerView.itemAnimator = DefaultItemAnimator()
        usersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )
        viewModel.savedUsers.observe(viewLifecycleOwner, Observer(usersAdapter::updateUsers))
    }

    private fun navigateToDetails(user: User) {
        navigateTo(UserFragment.newInstance(user))
    }

    private fun setupMenuListener() {
        fragmentUsersBinding.usersToolbar.setOnMenuItemClickListener { itemClicked ->

            if (itemClicked.itemId == R.id.menu_item_users_search) {
                showSearchToolbar()
                return@setOnMenuItemClickListener true
            }

            return@setOnMenuItemClickListener false
        }
    }

    private fun showSearchToolbar() {
        SearchUserDialogFragment.newInstance(viewModel)
            .show(parentFragmentManager, "dialog_fragment_search_user")
    }
}
