package io.github.messiaslima.codewars.ui.shared

import androidx.fragment.app.Fragment
import com.pd.chocobar.ChocoBar
import retrofit2.HttpException

fun Fragment.navigateTo(fragment: Fragment) {
    (activity as MainActivity).showFragment(fragment)
}

fun Fragment.showErrorMessage(message: String?, throwable: Throwable?) {

    var errorMessage = message

    if (throwable is HttpException){
        errorMessage = when(throwable.code()){
            404 -> "We cannot find the requested user"
            else -> {
                throwable.printStackTrace()
                message
            }
        }
    } else {
        throwable?.printStackTrace()
    }

    ChocoBar.builder()
        .setView(view)
        .setText(errorMessage)
        .setDuration(ChocoBar.LENGTH_LONG)
        .red()
        .show()
}