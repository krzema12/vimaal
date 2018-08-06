package it.krzeminski.vimaal.commands

data class TextChangeInProgress(val type: TextChangeType?)

enum class TextChangeType {
    DELETE_LINE
}

