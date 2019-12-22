package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.*
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsResult
import io.github.messiaslima.codewars.repository.user.UserRepository
import javax.inject.Inject

class UsersViewModel constructor(
    private val userRepository: UserRepository
) : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String>()

    val userFound: LiveData<User?> = Transformations.switchMap(_username, this::searchUserByUsename)

    override fun onSearchUser(username: String) {
        _isLoading.value = true
        _username.value = username
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(userRepository) as T
        }
    }

    private fun searchUserByUsename(username: String): LiveData<User?> {
        val userResult = userRepository.searchUser(username)
        return Transformations.map(userResult) {
            _isLoading.value = false
            if (it is CodewarsResult.Success) {
                return@map it.value
            } else {
                return@map null
            }
        }
    }
}

