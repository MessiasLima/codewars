package io.github.messiaslima.codewars.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.messiaslima.codewars.databinding.FragmentChallengesBinding
import kotlinx.android.synthetic.main.fragment_challenges.*

class ChallengesFragment : Fragment() {

    private lateinit var challengeType: ChallengeType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChallengesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        challengeName.text = challengeType.name
    }

    companion object {
        fun getInstance(challengeType: ChallengeType): ChallengesFragment {
            val fragment = ChallengesFragment()
            fragment.challengeType = challengeType
            return fragment
        }
    }
}