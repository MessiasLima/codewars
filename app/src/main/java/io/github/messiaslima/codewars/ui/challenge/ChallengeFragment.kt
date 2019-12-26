package io.github.messiaslima.codewars.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.github.messiaslima.codewars.databinding.FragmentChallengeBinding
import io.github.messiaslima.codewars.entity.Challenge
import kotlinx.android.synthetic.main.fragment_challenge.*

class ChallengeFragment : Fragment() {

    lateinit var viewModel: ChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val challenge = arguments?.getSerializable("challenge") as Challenge

        viewModel = ViewModelProviders.of(
            this,
            ChallengeViewModel.Factory(challenge)
        )[ChallengeViewModel::class.java]

        val binding = FragmentChallengeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBackButton()
    }

    private fun setupBackButton() {
        challengeToolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    companion object {

        fun getInstance(challenge: Challenge): ChallengeFragment {

            val bundle = bundleOf(
                Pair("challenge", challenge)
            )

            return ChallengeFragment().apply {
                arguments = bundle
            }
        }
    }
}