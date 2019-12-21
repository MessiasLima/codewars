package io.github.messiaslima.codewars.ui.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.github.messiaslima.codewars.R

class LoadingDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "io.github.messiaslima.codewars.LoadingDialogFragment"
    }

    init {
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_loading, container, false)
    }
}