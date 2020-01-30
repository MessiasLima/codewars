package io.github.messiaslima.codewars.ui.challenges

import androidx.lifecycle.*
import androidx.paging.PagedList
import io.github.messiaslima.codewars.entity.Challenge
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.challenge.ChallengeRepository
import io.github.messiaslima.codewars.util.Resource
import io.github.messiaslima.codewars.util.Status
import javax.inject.Inject

class ChallengesViewModel(
    private val user: User,
    private val challengeType: ChallengeType
) : ViewModel() {

    @Inject
    lateinit var challengeRepository: ChallengeRepository

    var endOfListMessageShown: Boolean = false

    private val _challengesResource: LiveData<Resource<PagedList<Challenge>>> by lazy {
        challengeRepository.findChallenges(user, challengeType)
    }

    private val _challenges by lazy {
        MediatorLiveData<PagedList<Challenge>>().also { mediator ->
            mediator.addSource(_challengesResource) { resource ->
                if (resource.status == Status.SUCCESS) {
                    mediator.value = resource.data
                }
            }
        }
    }
    val challenges: LiveData<PagedList<Challenge>> by lazy { _challenges }
    val isLoading: LiveData<Boolean> by lazy {
        _challengesResource.map { it.status == Status.LOADING }
    }

    init {
        DaggerChallengesComponent.create().inject(this)
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