package io.github.messiaslima.codewars.ui.user

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.github.messiaslima.codewars.ui.challenges.ChallengesFragment

class ChallengesPagerAdapter(
    private val userFragment: UserFragment,
    private val user: User
) : FragmentStateAdapter(userFragment) {

    override fun getItemCount(): Int {
        return ChallengeType.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return ChallengesFragment.getInstance(user, ChallengeType.values()[position])
    }
}