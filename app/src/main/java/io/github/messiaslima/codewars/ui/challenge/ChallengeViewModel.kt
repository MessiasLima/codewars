package io.github.messiaslima.codewars.ui.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.repository.challenge.ChallengeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChallengeViewModel(
    private val challenge: Challenge
) : ViewModel() {

    @Inject
    lateinit var challengeRepository: ChallengeRepository
    private val compositeDisposable = CompositeDisposable()

    private val _challengeObservable = MutableLiveData<Challenge>()
    val challengeObservable: LiveData<Challenge> = _challengeObservable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _challengeObservable.value = challenge
        DaggerChallengeComponent.create().inject(this)
        getChallengeDetails()
    }

    private fun getChallengeDetails() {
        _isLoading.value = true
        challengeRepository.findChallenge(challenge.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally { _isLoading.value = false }
            .subscribe({
                _challengeObservable.value = it
            },{

            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory(private val challenge: Challenge) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ChallengeViewModel(challenge) as T
        }
    }
}