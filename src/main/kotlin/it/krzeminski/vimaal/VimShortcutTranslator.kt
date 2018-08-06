package it.krzeminski.vimaal

import it.krzeminski.vimaal.commands.TextChangeInProgress
import it.krzeminski.vimaal.commands.TextChangeType.DELETE_LINE

class VimShortcutTranslator(
        private val textChangeListener: TextChangeListener,
        private var vimState: VimState = VimState()
) {
    fun keyPressed(key: Char) {
        if (key == 'd') {
            if (vimState.textChangeInProgress.type == null) {
                vimState = vimState.copy(textChangeInProgress = TextChangeInProgress(type = DELETE_LINE))
            } else if (vimState.textChangeInProgress.type == DELETE_LINE) {
                vimState = vimState.copy(textChangeInProgress = TextChangeInProgress(type = null))
                textChangeListener.onLinesRemoved(1)
            }
        }
    }
}

