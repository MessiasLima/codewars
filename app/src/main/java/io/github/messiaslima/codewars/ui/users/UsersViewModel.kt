package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.*
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.github.messiaslima.codewars.util.Event
import io.github.messiaslima.codewars.util.Resource
import io.github.messiaslima.codewars.util.Status
import javax.inject.Inject

class UsersViewModel : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    @Inject
    lateinit var userRepository: UserRepository

    private val _sortByHonor = MutableLiveData<Boolean>()
    val sortByHonor = _sortByHonor

    private val _loadUsersEvent = MutableLiveData<Unit>()

    private val _users = _loadUsersEvent.switchMap {
        userRepository.findSavedUsers(sortByHonor = sortByHonor.value == true)
    }

    private val _sortedUsers = _sortByHonor.switchMap {
        userRepository.findSavedUsers(sortByHonor = it)
    }

    val users = MediatorLiveData<Resource<List<User>>>().also { mediator ->

        mediator.addSource(_users) { userList ->
            mediator.value = userList
        }

        mediator.addSource(_sortedUsers) { userList ->
            mediator.value = userList
        }
    }

    private val _userQueryEvent = MutableLiveData<String>()
    private val _userClickedEvent = MutableLiveData<User>()
    private val _userFound = _userQueryEvent.switchMap { query ->
        userRepository.searchUserV2(query)
    }

    private val _goToDetailsEvent = MediatorLiveData<Event<Resource<User>>>().also { mediator->

        mediator.addSource(_userClickedEvent) { clickedUser ->
            mediator.value = Event(Resource.success(clickedUser))
        }

        mediator.addSource(_userFound) { userResource ->
            mediator.value = Event(userResource)
        }
    }
    val goToDetailsEvent: LiveData<Event<Resource<User>>> = _goToDetailsEvent

    private val _isLoading = MediatorLiveData<Boolean>().also { isLoadingMediator ->

        isLoadingMediator.addSource(users) { resource ->
            isLoadingMediator.value = resource.status == Status.LOADING
        }

        isLoadingMediator.addSource(_userFound) { resource ->
            isLoadingMediator.value = resource.status == Status.LOADING
        }
    }
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        DaggerUsersComponent.create().inject(this)
        _loadUsersEvent.value = Unit
    }

    override fun onSearchUser(username: String) {
        _userQueryEvent.value = username
    }

    fun onUserSelected(selectedUser: User) {
        _userClickedEvent.value = selectedUser
    }

    fun setSortByHonor(sortByHonor: Boolean) {
        _sortByHonor.value = sortByHonor
    }
}

