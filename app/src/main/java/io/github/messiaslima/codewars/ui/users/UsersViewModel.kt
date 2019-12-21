package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.messiaslima.codewars.repository.user.UserRepository
import javax.inject.Inject

class UsersViewModel constructor(
    private val userRepository: UserRepository
) : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    override fun onSearchUser(username: String) {
        _isLoading.value = true
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(userRepository) as T
        }
    }

}

