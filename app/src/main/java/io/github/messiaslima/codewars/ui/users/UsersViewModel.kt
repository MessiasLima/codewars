package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.messiaslima.codewars.Event
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersViewModel : ViewModel(), SearchUserDialogFragment.OnSearchUserListener {

    @Inject
    lateinit var userRepository: UserRepository
    private val compositeDisposable = CompositeDisposable()

    var isLoading = MutableLiveData<Boolean>()

    private val _savedUsers = MutableLiveData<List<User>>()
    val savedUsers: LiveData<List<User>> = _savedUsers

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    private val _goToDetailsEvent = MutableLiveData<Event<User>>()
    val goToDetailsEvent: LiveData<Event<User>> = _goToDetailsEvent

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
                _errorEvent.value = Event(throwable)
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
                _goToDetailsEvent.value = Event(user)
                findSavedUsers()
            }, {
                _errorEvent.value = Event(it)
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

