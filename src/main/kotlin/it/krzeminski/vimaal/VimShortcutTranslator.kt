package it.krzeminski.vimaal

import kotlin.math.max

class VimShortcutTranslator(
        private val textChangeListener: TextChangeListener,
        private val navigationListener: NavigationListener)
{
    private val vimStateMachine = buildVimStateMachine()

    fun keyPressed(key: Char) {
        val sideEffect = vimStateMachine.transition(VimEvent.Character(key))

        when (sideEffect) {
            is VimSideEffect.DeleteLine -> textChangeListener.onLinesRemoved(quantity = sideEffect.quantity)
            is VimSideEffect.DeleteCharacter -> textChangeListener.onCharactersRemoved(quantity = sideEffect.quantity)
            is VimSideEffect.GoToBeginningOfLine -> navigationListener.goToBeginningOfLine()
        }
    }

    sealed class VimState {
        object Initial : VimState()
        data class DeleteSomething(val times: Int) : VimState()
        data class RepeatNextAction(val times: Int) : VimState()
    }

    sealed class VimEvent {
        data class Character(val character: Char) : VimEvent()
    }

    sealed class VimSideEffect {
        data class DeleteCharacter(val quantity: Int) : VimSideEffect()
        data class DeleteLine(val quantity: Int) : VimSideEffect()
        object GoToBeginningOfLine : VimSideEffect()
    }

    private fun buildVimStateMachine() =
            stateMachine<VimState, VimEvent, VimSideEffect>(initialState = VimState.Initial) {
                VimState.Initial {
                    when (it) {
                        VimEvent.Character('0') -> go to VimState.Initial with VimSideEffect.GoToBeginningOfLine
                        in ('1'..'9').map { c -> VimEvent.Character(c) } ->
                            go to VimState.RepeatNextAction(
                                    times = "${(it as VimEvent.Character).character}".toInt())
                        VimEvent.Character('d') -> go to VimState.DeleteSomething(times = 1)
                        VimEvent.Character('x') ->
                            go to VimState.Initial with VimSideEffect.DeleteCharacter(quantity = 1)
                        else -> go to VimState.Initial
                    }
                }

                VimState.RepeatNextAction::class { it, currentState ->
                    when (it) {
                        in ('0'..'9').map { c -> VimEvent.Character(c) } ->
                            go to VimState.RepeatNextAction(
                                times = "${currentState.times}${(it as VimEvent.Character).character}".toInt())
                        VimEvent.Character('d') -> go to VimState.DeleteSomething(times = currentState.times)
                        VimEvent.Character('x') ->
                            go to VimState.Initial with VimSideEffect.DeleteCharacter(quantity = currentState.times)
                        else -> go to VimState.Initial
                    }
                }

                VimState.DeleteSomething::class { it, currentState ->
                    when (it) {
                        VimEvent.Character('d') ->
                            go to VimState.Initial with VimSideEffect.DeleteLine(quantity = max(currentState.times, 1))
                        else -> go to VimState.Initial
                    }
                }
            }
}
