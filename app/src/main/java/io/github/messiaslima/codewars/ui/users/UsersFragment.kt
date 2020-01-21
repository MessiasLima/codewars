package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUsersBinding
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.exception.ResourceNotFoundException
import io.github.messiaslima.codewars.ui.common.navigateTo
import io.github.messiaslima.codewars.ui.common.showErrorMessage
import io.github.messiaslima.codewars.ui.user.UserFragment
import io.github.messiaslima.codewars.util.EventObserver
import io.github.messiaslima.codewars.util.Status

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
        setupNavigationEvent()
    }

    private fun setupNavigationEvent() {
        viewModel.goToDetailsEvent.observe(viewLifecycleOwner, EventObserver { resource ->
            if (resource.status == Status.SUCCESS) {
                navigateToDetails(resource.data!!)
            } else {
                handleErrors(resource.throwable)
            }
        })
    }

    private fun setupUsersRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        val usersAdapter = UsersAdapter(viewModel)

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

        viewModel.users.observe(viewLifecycleOwner, Observer { response ->
            if (response.status == Status.SUCCESS) {
                usersAdapter.updateUsers(response.data!!)
            }
        })

    }

    private fun navigateToDetails(user: User) {
        navigateTo(UserFragment.newInstance(user))
    }

    private fun setupMenuListener() {
        fragmentUsersBinding.usersToolbar.setOnMenuItemClickListener { itemClicked ->
            return@setOnMenuItemClickListener when (itemClicked.itemId) {

                R.id.menu_item_users_search -> {
                    showSearchToolbar()
                    true
                }

                R.id.menu_item_users_filter -> {
                    showSortMenu()
                    return@setOnMenuItemClickListener true
                }

                else -> false
            }
        }
    }

    private fun showSortMenu() {

        val options = arrayOf(getString(R.string.order_by_rank))
        val checkedItems = BooleanArray(1) { viewModel.sortByHonor.value == true }

        AlertDialog.Builder(requireContext())
            .setNeutralButton(R.string.cancel, null)
            .setMultiChoiceItems(options, checkedItems) { dialog, which, isChecked ->
                if (which == 0) {
                    viewModel.setSortByHonor(isChecked)
                }
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showSearchToolbar() {
        SearchUserDialogFragment.newInstance(viewModel)
            .show(parentFragmentManager, "dialog_fragment_search_user")
    }


    private fun handleErrors(throwable: Throwable?) {

        when (throwable) {

            is ResourceNotFoundException -> {
                showErrorMessage(R.string.user_not_found)
            }

            else -> showErrorMessage(getString(R.string.unknown_error))
        }

    }
}
