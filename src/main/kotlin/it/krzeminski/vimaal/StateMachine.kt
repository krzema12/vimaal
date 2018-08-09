package it.krzeminski.vimaal

class StateMachine<StateType, EventType, SideEffectType>(initialState: StateType) {
    var currentState = initialState
        private set
    private val stateToTransitionMapping:
            MutableMap<StateType, (EventType) -> Transition<StateType, SideEffectType>> = mutableMapOf()

    /**
     * Perform a transition from [currentState], triggered by [event].
     */
    fun transition(event: EventType): SideEffectType? {
        val transition = stateToTransitionMapping[currentState]?.invoke(event)
        currentState = transition?.state ?: currentState
        return transition?.sideEffect
    }

    /**
     * Checks if any logic was set to go from [state] to any other state.
     */
    fun containsTransitionFor(state: StateType) = stateToTransitionMapping[state] != null

    data class Transition<StateType, SideEffectType>(val state: StateType, val sideEffect: SideEffectType?)

    /**
     * Used in DSL during building the state machine, to define a state and transitions from it.
     */
    operator fun StateType.invoke(function: (EventType) -> Transition<StateType, SideEffectType>) {
        if (stateToTransitionMapping.containsKey(this)) {
            throw IllegalArgumentException("State redefinition during building: ${this}")
        }
        stateToTransitionMapping[this] = function
    }

    /**
     * Used in DSL during building the state machine, to specify the target state during transition.
     */
    infix fun go.to(state: StateType): Transition<StateType, SideEffectType> =
            Transition(state = state, sideEffect = null)

    /**
     * Used in DSL during building the state machine, to specify the side effect of the transition.
     */
    infix fun Transition<StateType, SideEffectType>.with(sideEffect: SideEffectType): Transition<StateType, SideEffectType> =
            this.copy(sideEffect = sideEffect)
}

fun <StateType, EventType, SideEffectType> stateMachine(
        initialState: StateType,
        initialize: StateMachine<StateType, EventType, SideEffectType>.() -> Unit): StateMachine<StateType, EventType, SideEffectType> {
    val newStateMachine = StateMachine<StateType, EventType, SideEffectType>(initialState = initialState)
    newStateMachine.initialize()

    if (!newStateMachine.containsTransitionFor(initialState)) {
        throw IllegalArgumentException("No transitions have been defined for the initial state: $initialState")
    }

    return newStateMachine
}

/**
 * Used in DSL during building.
 */
object go
