package it.krzeminski.vimaal

import io.kotlintest.specs.StringSpec
import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify

class VimShortcutTranslatorSpec : StringSpec({

    // TODO move objects init to some 'beforeEach'

    "pressing 'd' should do nothing" {
        val textChangeListenerMock = spyk<TextChangeListener>()
        val vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock)
        vimShortcutTranslator.keyPressed('d')

        verify { textChangeListenerMock wasNot Called }
    }

    "pressing 'dd' should remove one line" {
        val textChangeListenerMock = spyk<TextChangeListener>()
        val vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock)
        repeat(2) { vimShortcutTranslator.keyPressed('d') }

        verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
    }

    "pressing 'dd' twice should remove two lines" {
        val textChangeListenerMock = spyk<TextChangeListener>()
        val vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock)
        repeat(4) { vimShortcutTranslator.keyPressed('d') }

        verify(exactly = 2) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
    }
})
