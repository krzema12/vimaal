package it.krzeminski.vimaal

import org.junit.Assert.assertEquals
import org.specnaz.kotlin.junit.SpecnazKotlinJUnit

sealed class MyState {
    object InitialState : MyState()
    object SomeState2 : MyState()
    object SomeState3 : MyState()
}

sealed class MyEvent {
    object Back : MyEvent()
    data class CharacterEvent(val character: Char) : MyEvent()
}

sealed class MySideEffect {
    object SideEffect1 : MySideEffect()
}

class StateMachineSpec : SpecnazKotlinJUnit(StateMachine::class.simpleName!!, { it ->
    lateinit var stateMachineUnderTest: StateMachine<MyState, MyEvent, MySideEffect>

    it.describes("a correctly built state machine") {
        it.beginsEach { _ ->
            stateMachineUnderTest = stateMachine(initialState = MyState.InitialState) {
                MyState.InitialState {
                    when (it) {
                        MyEvent.CharacterEvent('g') -> go to MyState.SomeState2
                        MyEvent.CharacterEvent('p') -> go to MyState.SomeState3 with MySideEffect.SideEffect1
                        else -> go to MyState.SomeState3
                    }
                }

                MyState.SomeState2 {
                    when (it) {
                        MyEvent.Back -> go to MyState.InitialState
                        else -> go to MyState.SomeState3
                    }
                }

                MyState.SomeState3 {
                    go to MyState.SomeState2
                }
            }
        }

        it.should("initially return the initial state") {
            assertEquals(stateMachineUnderTest.currentState, MyState.InitialState)
        }

        it.describes("transitioning between states") {
            it.should("transition from one state to the other") {
                stateMachineUnderTest.transition(MyEvent.CharacterEvent('g'))

                assertEquals(stateMachineUnderTest.currentState, MyState.SomeState2)
            }

            it.should("transition back and forth") {
                stateMachineUnderTest.transition(MyEvent.CharacterEvent('g'))
                stateMachineUnderTest.transition(MyEvent.Back)

                assertEquals(stateMachineUnderTest.currentState, MyState.InitialState)
            }

            it.should("return a null side effect if none given for transition") {
                val sideEffect = stateMachineUnderTest.transition(MyEvent.CharacterEvent('g'))

                assertEquals(sideEffect, null)
            }

            it.should("return an appropriate side effect if some was given for transition") {
                val sideEffect = stateMachineUnderTest.transition(MyEvent.CharacterEvent('p'))

                assertEquals(sideEffect, MySideEffect.SideEffect1)
            }
        }
    }

    it.describes("building an invalid state machine") {
        it.shouldThrow<IllegalArgumentException>(
                "when initialState is not mentioned as state in defining transitions") {
            stateMachine<MyState, MyEvent, MySideEffect>(initialState = MyState.InitialState) {
                MyState.SomeState2 {
                    go to MyState.SomeState3
                }
            }
        }.withMessageStartingWith("No transitions have been defined for the initial state")
         .withMessageContaining("InitialState")

        it.shouldThrow<IllegalArgumentException>("when states are repeated") {
            stateMachine<MyState, MyEvent, MySideEffect>(initialState = MyState.InitialState) {
                MyState.InitialState {
                    go to MyState.InitialState
                }

                MyState.InitialState {
                    go to MyState.InitialState
                }
            }
        }.withMessageStartingWith("State redefinition during building")
         .withMessageContaining("InitialState")
    }
})
