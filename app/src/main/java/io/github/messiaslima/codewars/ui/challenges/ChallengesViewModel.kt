package io.github.messiaslima.codewars.ui.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.challenge.ChallengeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChallengesViewModel(
    private val user: User,
    private val challengeType: ChallengeType,
    private val view: ChallengesContract.View
) : ViewModel() {

    @Inject
    lateinit var challengeRepository: ChallengeRepository
    private val compositeDisposable = CompositeDisposable()

    var loading = MutableLiveData<Boolean>()

    private val _challenges = MutableLiveData<List<Challenge>>()
    val challenges: LiveData<List<Challenge>> = _challenges

    init {
        DaggerChallengeComponent.create().inject(this)
        searchChallenges()
    }

    private fun searchChallenges() {
        loading.value = true
        challengeRepository.findChallenges(user, challengeType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally {
                loading.value = false
            }
            .subscribe({ challenges ->
                _challenges.value = challenges
            }, { throwable ->
                view.handleError(throwable)
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory(
        private val user: User,
        private val challengeType: ChallengeType,
        private val view: ChallengesContract.View
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ChallengesViewModel(user, challengeType, view) as T
        }
    }
}