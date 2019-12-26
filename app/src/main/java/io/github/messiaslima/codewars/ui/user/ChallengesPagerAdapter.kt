package io.github.messiaslima.codewars.ui.user

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import io.github.messiaslima.codewars.ui.challenges.ChallengesFragment

class ChallengesPagerAdapter(userFragment: UserFragment) : FragmentStateAdapter(userFragment){

    override fun getItemCount(): Int {
        return ChallengeType.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return ChallengesFragment.getInstance(ChallengeType.values()[position])
    }
}