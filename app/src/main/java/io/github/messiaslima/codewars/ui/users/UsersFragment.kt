package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUsersBinding
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.shared.LoadingDialogFragment
import io.github.messiaslima.codewars.ui.shared.navigateTo
import io.github.messiaslima.codewars.ui.user.UserFragment
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class UsersFragment : Fragment() {

    @Inject
    lateinit var userViewModelFactory: UsersViewModel.Factory

    lateinit var viewModel: UsersViewModel
    private var loadingDialogFragment: LoadingDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerUsersComponent.create().inject(this)

        viewModel = ViewModelProviders.of(this, userViewModelFactory)[UsersViewModel::class.java]

        val fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        fragmentUsersBinding.lifecycleOwner = this
        fragmentUsersBinding.viewModel = viewModel

        return fragmentUsersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuListener()
        setupLoadingListener()
        setupUserFoundListener()
    }

    private fun setupUserFoundListener() {
        viewModel.userFound.observe(this, Observer { user ->
            user?.let(this::goToDetails)
        })
    }

    private fun setupLoadingListener() {
        viewModel.isLoading.observe(this, Observer { isLoading ->

            if (loadingDialogFragment == null) {
                loadingDialogFragment = LoadingDialogFragment()
            }

            if (isLoading) {
                loadingDialogFragment?.show(fragmentManager!!, LoadingDialogFragment.TAG)
            } else {
                loadingDialogFragment?.dismiss()
            }

        })
    }

    private fun goToDetails(user: User) {
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
}
