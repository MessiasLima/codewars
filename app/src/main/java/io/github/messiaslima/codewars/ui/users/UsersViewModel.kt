package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.*
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsResult
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.github.messiaslima.codewars.ui.shared.Event
import javax.inject.Inject

class UsersViewModel constructor(
    private val view: UsersContract.View
) : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    @Inject
    lateinit var userRepository: UserRepository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String>()

    val userFound: LiveData<Event<User?>> =
        Transformations.switchMap(_username, this::searchUserByUsename)

    init {
        DaggerUsersComponent.create().inject(this)
    }

    override fun onSearchUser(username: String) {
        _isLoading.value = true
        _username.value = username
    }

    class Factory constructor(
        private val view: UsersContract.View
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(view) as T
        }
    }

    private fun searchUserByUsename(username: String): LiveData<Event<User?>> {
        val userResultLiveData = userRepository.searchUser(username)
        val userLiveData = transformResultToUserLiveData(userResultLiveData)
        return Transformations.map(userLiveData) { return@map Event(it) }
    }

    private fun transformResultToUserLiveData(userResultLiveData: LiveData<CodewarsResult<User>>): LiveData<User?> {
        return Transformations.map(userResultLiveData) {
            _isLoading.value = false
            if (it is CodewarsResult.Success) {
                return@map it.value
            } else {
                return@map null
            }
        }
    }
}

