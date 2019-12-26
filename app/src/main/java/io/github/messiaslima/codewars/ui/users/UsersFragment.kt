package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUsersBinding
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.shared.LoadingDialogFragment
import io.github.messiaslima.codewars.ui.shared.navigateTo
import io.github.messiaslima.codewars.ui.shared.showErrorMessage
import io.github.messiaslima.codewars.ui.user.UserFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.list_item_user.*

class UsersFragment : Fragment(), UsersContract.View {

    lateinit var viewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProviders.of(this, UsersViewModel.Factory(this))[UsersViewModel::class.java]

        val fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        fragmentUsersBinding.lifecycleOwner = this
        fragmentUsersBinding.viewModel = viewModel

        return fragmentUsersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuListener()
        setupUsersRecyclerView()
    }

    private fun setupUsersRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        val usersAdapter = UsersAdapter(this::navigateToDetails)

        usersRecyclerView.adapter = usersAdapter
        usersRecyclerView.layoutManager = layoutManager
        usersRecyclerView.itemAnimator = DefaultItemAnimator()
        usersRecyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        viewModel.savedUsers.observe(this, Observer(usersAdapter::updateUsers))
    }

    override fun navigateToDetails(user: User) {
        navigateTo(UserFragment.newInstance(user))
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

    private fun showSearchToolbar() {
        fragmentManager?.let {
            SearchUserDialogFragment.newInstance(viewModel)
                .show(it, "dialog_fragment_search_user")
        }
    }

    override fun handleError(message: String, throwable: Throwable?) {
        showErrorMessage(message, throwable)
    }
}
