package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.github.messiaslima.codewars.R
import kotlinx.android.synthetic.main.dialog_fragment_search_user.*

class SearchUserDialogFragment : DialogFragment() {

    lateinit var onSearchUserListener: OnSearchUserListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_search_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchDialogButton.setOnClickListener {
            onSearchUserListener.onSearchUser(userSearchEditText.editableText.toString())
            dismiss()
        }
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
