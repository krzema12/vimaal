package it.krzeminski.vimaal

import it.krzeminski.vimaal.state.VimState
import it.krzeminski.vimaal.state.transformState

class VimShortcutTranslator(
        private val textChangeListener: TextChangeListener,
        private var vimState: VimState = VimState()
) {
    fun keyPressed(key: Char) {
        vimState = transformState(vimState, key)
        vimState = act(vimState)
    }

    private fun act(vimState: VimState): VimState {
        if (vimState.keyPressContext == "dd") {
            textChangeListener.onLinesRemoved(IntRange(vimState.cursorPosition.row, vimState.cursorPosition.row))
            return vimState.copy(keyPressContext = "")
        }
        return vimState
    }
}