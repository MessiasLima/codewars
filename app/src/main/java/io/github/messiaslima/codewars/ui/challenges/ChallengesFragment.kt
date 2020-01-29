package io.github.messiaslima.codewars.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pd.chocobar.ChocoBar
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.databinding.FragmentChallengesBinding
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.ui.challenge.ChallengeFragment
import io.github.messiaslima.codewars.ui.common.navigateTo
import io.github.messiaslima.codewars.ui.common.showErrorMessage
import kotlinx.android.synthetic.main.fragment_challenges.*

class ChallengesFragment : Fragment() {

    private val user: User by lazy {
        arguments?.get("user") as User
    }

    private val challengeType: ChallengeType by lazy {
        arguments?.get("challengeType") as ChallengeType
    }

    private val viewModel: ChallengesViewModel by viewModels {
        ChallengesViewModel.Factory(user, challengeType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentChallengesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChallengesRecyclerView()
        setupLoadingListener()
    }

    private fun setupLoadingListener() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
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
        viewModel.challenges.observe(viewLifecycleOwner, Observer(adapter::submitList))
    }

    private fun showChallengeDetails(challenge: Challenge){
        navigateTo(ChallengeFragment.getInstance(challenge))
    }

    fun handleError(throwable: Throwable?) {
        showErrorMessage(getString(R.string.error_getting_completed_challenges))
    }

    fun showEndOfResultsMessage() {

        ChocoBar.builder()
            .setView(view)
            .setText(R.string.end_of_results_message)
            .setDuration(ChocoBar.LENGTH_LONG)
            .orange()
            .show()

        viewModel.endOfListMessageShown = true
    }

    companion object {
        fun getInstance(user: User, challengeType: ChallengeType): ChallengesFragment {

            val bundle = bundleOf(
                Pair("user", user),
                Pair("challengeType", challengeType)
            )

            val fragment = ChallengesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}