package it.krzeminski.vimaal

import it.krzeminski.vimaal.commands.TextChangeInProgress

data class VimState(
        val textChangeInProgress: TextChangeInProgress = TextChangeInProgress(type = null))

