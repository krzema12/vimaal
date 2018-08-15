package it.krzeminski.vimaal

interface NavigationListener {
    fun goToBeginningOfLine()
    fun moveByCharacters(direction: Direction, distance: Int)
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}
