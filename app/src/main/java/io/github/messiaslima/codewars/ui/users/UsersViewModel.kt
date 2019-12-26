package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersViewModel constructor(
    private val view: UsersContract.View
) : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    @Inject
    lateinit var userRepository: UserRepository
    private val compositeDisposable = CompositeDisposable()

    var isLoading = MutableLiveData<Boolean>()

    private val _savedUsers = MutableLiveData<List<User>>()
    val savedUsers: LiveData<List<User>> = _savedUsers

    init {
        DaggerUsersComponent.create().inject(this)
        findSavedUsers()
    }

    private fun findSavedUsers() {
        isLoading.value = true
        userRepository.findSavedUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally{
                isLoading.value = false
            }.subscribe({ users ->
                _savedUsers.value = users
            },{ throwable ->
                view.handleError("Error getting saved users", throwable)
            }).addTo(compositeDisposable)

    }

    override fun onSearchUser(username: String) {
        searchUserByUsename(username)
    }

    private fun searchUserByUsename(username: String) {
        isLoading.value = true
        userRepository.searchUser(username)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally {
                isLoading.value = false
            }
            .subscribe({ user ->
                view.navigateToDetails(user)
                findSavedUsers()
            }, {
                view.handleError("Ops! Something was wrong getting the user", it)
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory constructor(
        private val view: UsersContract.View
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(view) as T
        }
    }
}

