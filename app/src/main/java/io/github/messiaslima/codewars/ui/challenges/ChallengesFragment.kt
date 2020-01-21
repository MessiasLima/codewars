package io.github.messiaslima.codewars.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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

    private lateinit var user: User
    private lateinit var challengeType: ChallengeType
    private lateinit var viewModel: ChallengesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        user = arguments?.get("user") as User
        challengeType = arguments?.get("challengeType") as ChallengeType

        viewModel = ViewModelProviders.of(
            this,
            ChallengesViewModel.Factory(user, challengeType)
        )[ChallengesViewModel::class.java]

        val binding = FragmentChallengesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChallengesRecyclerView()
        setupLoadingListener()
        setupScrollListener()
    }

    private fun setupScrollListener() {
        challengesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = challengesRecyclerView.layoutManager as LinearLayoutManager

                if (shouldGetNextPage(layoutManager)) {
                    viewModel.getNextPage()
                }

                if (
                    isNotLoading() &&
                    theLastItemIsVisible(layoutManager) &&
                    viewModel.itReachedTheEndOfList() &&
                    !viewModel.endOfListMessageShown
                ){
                    showEndOfResultsMessage()
                }
            }

            private fun shouldGetNextPage(layoutManager: LinearLayoutManager): Boolean{
                return isNotLoading() &&
                        theLastItemIsVisible(layoutManager) &&
                        !viewModel.itReachedTheEndOfList()
            }

            private fun theLastItemIsVisible(layoutManager: LinearLayoutManager) =
                layoutManager.findLastVisibleItemPosition() == (challengesRecyclerView.adapter?.itemCount
                    ?: 0) - 1

            private fun isNotLoading(): Boolean {
                return viewModel.isLoading.value == false
            }


        })
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
        viewModel.challenges.observe(viewLifecycleOwner, Observer(adapter::addChallenges))
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