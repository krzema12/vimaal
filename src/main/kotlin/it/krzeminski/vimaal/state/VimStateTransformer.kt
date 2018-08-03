package it.krzeminski.vimaal.state

fun transformState(vimState: VimState, key: Char): VimState {
    return vimState.copy(keyPressContext = vimState.keyPressContext + key)
}
