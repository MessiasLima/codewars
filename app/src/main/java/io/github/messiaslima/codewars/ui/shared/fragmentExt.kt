package io.github.messiaslima.codewars.ui.shared

import androidx.fragment.app.Fragment

fun Fragment.navigateTo(fragment: Fragment) {
    (activity as MainActivity).showFragment(fragment)
}