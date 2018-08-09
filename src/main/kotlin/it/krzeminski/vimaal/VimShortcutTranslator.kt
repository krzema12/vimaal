package it.krzeminski.vimaal

class VimShortcutTranslator(
        private val textChangeListener: TextChangeListener)
{
    private val vimStateMachine = buildVimStateMachine()

    fun keyPressed(key: Char) {
        val sideEffect = vimStateMachine.transition(VimEvent.Character(key))

        if (sideEffect == VimSideEffect.DeleteLine) {
            textChangeListener.onLinesRemoved(1)
        }
    }

    sealed class VimState {
        object Initial : VimState()
        object DeleteSomething : VimState()
    }

    sealed class VimEvent {
        data class Character(val character: Char) : VimEvent()
    }

    sealed class VimSideEffect {
        object DeleteLine : VimSideEffect()
    }

    private fun buildVimStateMachine() =
            stateMachine<VimState, VimEvent, VimSideEffect>(initialState = VimState.Initial) {
                VimState.Initial {
                    when (it) {
                        VimEvent.Character('d') -> go to VimState.DeleteSomething
                        else -> go to VimState.Initial
                    }
                }

                VimState.DeleteSomething {
                    when (it) {
                        VimEvent.Character('d') -> go to VimState.Initial with VimSideEffect.DeleteLine
                        else -> go to VimState.Initial
                    }
                }
            }
}
