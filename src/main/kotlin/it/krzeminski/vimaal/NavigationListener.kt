package it.krzeminski.vimaal

interface NavigationListener {
    fun goToLine(lineReference: LineReference)
    fun goToBeginningOfLine()
    fun moveByCharacters(direction: Direction, distance: Int)
}

sealed class LineReference {
    data class Number(val lineNumber: Int) : LineReference()
    object Last : LineReference()
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}
