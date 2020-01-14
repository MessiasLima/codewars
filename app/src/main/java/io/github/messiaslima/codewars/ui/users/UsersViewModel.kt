package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.*
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
    private val _loadUsersEvent = MutableLiveData<Unit>()
    var sortByRank = false

    val savedUsers: LiveData<List<User>> = _loadUsersEvent.switchMap {

        val usersLiveData = userRepository.findSavedUsers()

        return@switchMap if (sortByRank) {

            usersLiveData.map { users ->
                users.sortedByDescending { it.honor }
            }

        } else {
            usersLiveData
        }
    }

    private val _errorEvent = MutableLiveData<Event<Throwable>>()
    val errorEvent: LiveData<Event<Throwable>> = _errorEvent

    private val _goToDetailsEvent = MutableLiveData<Event<User>>()
    val goToDetailsEvent: LiveData<Event<User>> = _goToDetailsEvent

    init {
        DaggerUsersComponent.create().inject(this)
        loadUsers()
    }

    fun loadUsers() {
        _loadUsersEvent.value = Unit
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
            }, {
                _errorEvent.value = Event(it)
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onUserSelected(selectedUser: User) {
        _goToDetailsEvent.value = Event(selectedUser)
    }
}

