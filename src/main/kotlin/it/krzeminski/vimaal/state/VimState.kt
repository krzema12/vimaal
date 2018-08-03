package it.krzeminski.vimaal.state

data class VimState(
        val cursorPosition: CursorPosition = CursorPosition.initial,
        val keyPressContext: String = "")

data class CursorPosition(
        val row: Int,
        val column: Int) {
    companion object {
        val initial = CursorPosition(1, 1)
    }
}
