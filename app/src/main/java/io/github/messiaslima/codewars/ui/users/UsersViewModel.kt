package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.*
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.common.api.ApiSuccessResponse
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.github.messiaslima.codewars.util.Event
import javax.inject.Inject

class UsersViewModel : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    @Inject
    lateinit var userRepository: UserRepository

    val isLoading = MutableLiveData<Boolean>()

    private val _sortByHonor = MutableLiveData<Boolean>()
    val sortByHonor = _sortByHonor

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    private val _loadUsersEvent = MutableLiveData<Unit>()

    private val _users = _loadUsersEvent.switchMap {
        userRepository.findSavedUsers(sortByHonor = sortByHonor.value == true)
    }

    private val _sortedUsers = _sortByHonor.switchMap {
        userRepository.findSavedUsers(sortByHonor = it)
    }

    val users = MediatorLiveData<List<User>>().also { mediator ->

        mediator.addSource(_users) { userList ->
            mediator.value = userList
        }

        mediator.addSource(_sortedUsers) { userList ->
            mediator.value = userList
        }
    }

    private val _userQuery = MutableLiveData<String>()

    private val _goToDetailsEvent = MutableLiveData<Event<User>>()

    val goToDetailsEvent: LiveData<Event<User>> = _userQuery.switchMap {
        userRepository.searchUserV2(it)
    }.map {
        Event((it as ApiSuccessResponse).body)
    }

    init {
        DaggerUsersComponent.create().inject(this)
        _loadUsersEvent.value = Unit
    }

    override fun onSearchUser(username: String) {
        _userQuery.value = username
    }

    fun onUserSelected(selectedUser: User) {
        _goToDetailsEvent.value = Event(selectedUser)
    }

    fun setSortByHonor(sortByHonor: Boolean) {
        _sortByHonor.value = sortByHonor
    }
}

