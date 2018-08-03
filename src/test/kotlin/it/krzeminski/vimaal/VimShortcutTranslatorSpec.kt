package it.krzeminski.vimaal

import io.kotlintest.specs.StringSpec
import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify

class VimShortcutTranslatorSpec : StringSpec({
    val textChangeListenerMock = spyk<TextChangeListener>()
    val vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock)

    "pressing 'd' should do nothing" {
        vimShortcutTranslator.keyPressed('d')

        verify { textChangeListenerMock wasNot Called }
    }

    "pressing 'dd' should remove the current line" {
        repeat(2) { vimShortcutTranslator.keyPressed('d') }

        verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(1..1) }
    }
})
