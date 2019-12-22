package io.github.messiaslima.codewars.ui.users

import androidx.lifecycle.*
import io.github.messiaslima.codewars.entity.User
import io.github.messiaslima.codewars.repository.shared.CodewarsResult
import io.github.messiaslima.codewars.repository.user.UserRepository
import io.github.messiaslima.codewars.ui.shared.Event
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

    init {
        DaggerUsersComponent.create().inject(this)
    }

    override fun onSearchUser(username: String) {
        searchUserByUsename(username)
    }

    class Factory constructor(
        private val view: UsersContract.View
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(view) as T
        }
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
            }, {
                view.handleError("Ops! Something was wrong getting the user", it)
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

