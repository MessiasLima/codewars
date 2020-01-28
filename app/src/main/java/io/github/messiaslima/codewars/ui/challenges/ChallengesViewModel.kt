package io.github.messiaslima.codewars.ui.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
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
    private val challengeType: ChallengeType
) : ViewModel() {

    var endOfListMessageShown: Boolean = false
    @Inject
    lateinit var challengeRepository: ChallengeRepository
    private val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    val challenges: LiveData<PagedList<Challenge>> by lazy {
        challengeRepository.findChallengesV2()
    }

    private var page = 0
    private var firstPageSize: Int? = null
    private var currentPageSize: Int? = null

    init {
        DaggerChallengesComponent.create().inject(this)
        searchChallenges(page)
    }

    private fun searchChallenges(page: Int) {
        _isLoading.value = true
        challengeRepository.findChallenges(user, challengeType, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally {
                _isLoading.value = false
            }
            .subscribe({ challenges ->
                updatePaginationVariables(challenges.size)
            }, { throwable ->

            })
            .addTo(compositeDisposable)
    }

    private fun updatePaginationVariables(size: Int) {
        page++
        if (firstPageSize == null) {
            firstPageSize = size
        }
        currentPageSize = size
    }

    fun itReachedTheEndOfList() = (firstPageSize ?: 0) > (currentPageSize ?: 0)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getNextPage() {
        searchChallenges(page)
    }

    class Factory(
        private val user: User,
        private val challengeType: ChallengeType
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ChallengesViewModel(user, challengeType) as T
        }
    }
}