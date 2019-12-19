package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUsersBinding
import io.github.messiaslima.codewars.ui.shared.BaseFragment

class UsersFragment : BaseFragment() {

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
    }
}
