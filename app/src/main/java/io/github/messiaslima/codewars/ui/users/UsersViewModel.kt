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

    var isLoading = MutableLiveData<Boolean>()
    private val _loadUsersEvent = MutableLiveData<Unit>()

    var sortByRank = false

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    val savedUsers: LiveData<List<User>> = _loadUsersEvent.switchMap {
        return@switchMap if (sortByRank) {
            sortUsersByHonor(userRepository.findSavedUsers())
        } else {
            userRepository.findSavedUsers()
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
        loadUsers()
    }

    private fun sortUsersByHonor(usersLiveData: LiveData<List<User>>) = usersLiveData.map { users ->
        users.sortedByDescending { it.honor }
    }

    fun loadUsers() {
        _loadUsersEvent.value = Unit
    }

    override fun onSearchUser(username: String) {
        _userQuery.value = username
    }

    fun onUserSelected(selectedUser: User) {
        _goToDetailsEvent.value = Event(selectedUser)
    }
}

