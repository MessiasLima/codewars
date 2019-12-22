package io.github.messiaslima.codewars.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.entity.User

class UserFragment : Fragment() {

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    companion object {
        fun newInstance(user: User): UserFragment {
            return UserFragment().apply {
                this.user = user
            }
        }
    }
}