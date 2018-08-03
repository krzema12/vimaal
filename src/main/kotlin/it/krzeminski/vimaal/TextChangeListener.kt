package it.krzeminski.vimaal

interface TextChangeListener {
    fun onLinesRemoved(removedLines: IntRange)
}