package io.github.messiaslima.codewars.ui.challenges

interface ChallengesContract {
    interface View {
        fun handleError(throwable: Throwable?)
    }
}