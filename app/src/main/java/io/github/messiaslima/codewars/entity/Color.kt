package io.github.messiaslima.codewars.entity

enum class Color(val value: String) {
    BLUE("blue"),
    PURPLE("purple"),
    WHITE("white"),
    YELLOW("yellow");

    companion object {
        fun findByValue(value: String): Color? {
            return values().find { it.value == value }
        }
    }
}