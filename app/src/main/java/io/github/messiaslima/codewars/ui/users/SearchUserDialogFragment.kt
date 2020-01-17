package io.github.messiaslima.codewars.ui.users

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import io.github.messiaslima.codewars.R
import kotlinx.android.synthetic.main.dialog_fragment_search_user.*
import java.io.Serializable

class SearchUserDialogFragment : DialogFragment() {

    private var onSearchUserListener: OnSearchUserListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onSearchUserListener = arguments?.getSerializable("onSearchListener") as OnSearchUserListener?
        return inflater.inflate(R.layout.dialog_fragment_search_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchDialogButton.setOnClickListener {
            triggerSearchListener()
        }

        userSearchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                triggerSearchListener()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun triggerSearchListener() {
        onSearchUserListener?.onSearchUser(userSearchEditText.editableText.toString())
        dismiss()
    }

    companion object {
        fun newInstance(onSearchUserListener: OnSearchUserListener): SearchUserDialogFragment {

            val arguments = bundleOf(
                Pair("onSearchListener", onSearchUserListener)
            )

            return SearchUserDialogFragment().apply {
                this.arguments = arguments
            }
        }
    }

    interface OnSearchUserListener : Serializable {
        fun onSearchUser(username: String)
    }
}
