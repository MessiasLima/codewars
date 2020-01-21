package io.github.messiaslima.codewars.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentUserBinding
import io.github.messiaslima.codewars.entity.User
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    val user: User by lazy {
        arguments?.getSerializable("user") as User
    }

    val viewModel by viewModels<UserViewModel>(factoryProducer = {
        UserViewModel.Factory(user)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuListener()
        setupPageAdapter()
        setupBottomNavigationLitener()
    }

    private fun setupBottomNavigationLitener() {
        userBottomNavigation.setOnNavigationItemSelectedListener { selectedItem ->

            val currentItem = when (selectedItem.itemId) {
                R.id.menu_item_completed_challenges -> 0
                R.id.menu_item_authored_challenges -> 1
                else -> 0
            }

            challengesPager.setCurrentItem(currentItem, true)

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setupPageAdapter() {
        challengesPager.adapter = ChallengesPagerAdapter(this, user)
        challengesPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    userBottomNavigation.selectedItemId = R.id.menu_item_completed_challenges
                } else {
                    userBottomNavigation.selectedItemId = R.id.menu_item_authored_challenges
                }
            }
        })
    }

    private fun setupMenuListener() {
        userToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(user: User): UserFragment {

            val bundle = bundleOf(
                Pair("user", user)
            )

            val fragment = UserFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}