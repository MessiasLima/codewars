package io.github.messiaslima.codewars.ui.shared

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.pd.chocobar.ChocoBar
import retrofit2.HttpException

fun Fragment.navigateTo(fragment: Fragment) {
    (activity as MainActivity).showFragment(fragment)
}

fun Fragment.showErrorMessage(@StringRes message: Int) {
    showErrorMessage(getString(message))
}

fun Fragment.showErrorMessage(message: String?) {
    showMessage(message, MessageType.ERROR)
}

fun Fragment.showMessage(message: String?, type: MessageType = MessageType.INFO) {

    val chocoBarBuilder = ChocoBar.builder()
        .setView(view)
        .setText(message)
        .setDuration(ChocoBar.LENGTH_LONG)


    val snackBar = when(type){
        MessageType.ERROR -> chocoBarBuilder.red()
        MessageType.SUCCESS -> chocoBarBuilder.green()
        MessageType.INFO -> chocoBarBuilder.build()
        MessageType.WARNING -> chocoBarBuilder.orange()
    }

    snackBar.show()
}

enum class MessageType {
    ERROR, SUCCESS, INFO, WARNING
}