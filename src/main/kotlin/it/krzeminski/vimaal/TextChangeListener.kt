package it.krzeminski.vimaal

interface TextChangeListener {
    fun onLinesRemoved(quantity: Int)
    fun onCharactersRemoved(quantity: Int)
}

