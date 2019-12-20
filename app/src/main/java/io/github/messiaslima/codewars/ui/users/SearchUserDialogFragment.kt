package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.github.messiaslima.codewars.R

class SearchUserDialogFragment : DialogFragment() {

    lateinit var onSearchUserListener: OnSearchUserListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_user, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(onSearchUserListener: OnSearchUserListener) = SearchUserDialogFragment().apply {
            this.onSearchUserListener = onSearchUserListener
        }
    }

    interface OnSearchUserListener {
        fun onSearchUser(username: String)
    }
}
