package io.github.messiaslima.codewars.repository.shared

sealed class CodewarsResult<out T : Any> {
    data class Success<out T : Any>(val value: T) : CodewarsResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : CodewarsResult<Nothing>()
}