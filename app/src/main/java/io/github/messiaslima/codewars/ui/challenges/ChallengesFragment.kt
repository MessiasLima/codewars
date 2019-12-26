package io.github.messiaslima.codewars.ui.challenges

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
import io.github.messiaslima.codewars.databinding.FragmentChallengesBinding
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.shared.showErrorMessage
import kotlinx.android.synthetic.main.fragment_challenges.*

class ChallengesFragment : Fragment(), ChallengesContract.View {

    private lateinit var user: User
    private lateinit var challengeType: ChallengeType
    private lateinit var viewModel: ChallengesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(
            this,
            ChallengesViewModel.Factory(user, challengeType, this)
        )[ChallengesViewModel::class.java]

        val binding = FragmentChallengesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChallengesRecyclerView()
        setupLoadingListener()
    }

    private fun setupLoadingListener() {
        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun setupChallengesRecyclerView() {

        val layoutManager = LinearLayoutManager(context)
        val adapter = ChallengesAdapter(this::showChallengeDetails)

        challengesRecyclerView.adapter = adapter
        challengesRecyclerView.layoutManager = layoutManager
        challengesRecyclerView.itemAnimator = DefaultItemAnimator()
        challengesRecyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        viewModel.challenges.observe(this, Observer(adapter::addChallenges))
    }

    private fun showChallengeDetails(challenge: Challenge){

    }

    override fun handleError(throwable: Throwable?) {
        showErrorMessage(getString(R.string.error_getting_completed_challenges), throwable)
    }

    companion object {
        fun getInstance(user: User, challengeType: ChallengeType): ChallengesFragment {
            val fragment = ChallengesFragment()
            fragment.challengeType = challengeType
            fragment.user = user
            return fragment
        }
    }
}