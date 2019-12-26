package io.github.messiaslima.codewars.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.github.messiaslima.codewars.databinding.FragmentUserBinding
import io.github.messiaslima.codewars.entity.User
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment(), UserContract.View {

    lateinit var user: User
    lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(
            this,
            UserViewModel.Factory(this, user)
        )[UserViewModel::class.java]

        val binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuListener()
    }

    private fun setupMenuListener() {
        userToolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    companion object {
        fun newInstance(user: User): UserFragment {
            return UserFragment().apply {
                this.user = user
            }
        }
    }
}