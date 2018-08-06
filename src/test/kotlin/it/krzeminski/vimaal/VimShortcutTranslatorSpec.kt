package it.krzeminski.vimaal

import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify
import org.specnaz.kotlin.junit.SpecnazKotlinJUnit

class VimShortcutTranslatorSpec : SpecnazKotlinJUnit(VimShortcutTranslator::class.simpleName!!, { it ->
    lateinit var textChangeListenerMock: TextChangeListener
    lateinit var vimShortcutTranslator: VimShortcutTranslator

    it.beginsEach {
        textChangeListenerMock = spyk()
        vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock)
    }

    it.describes("deleting") {
        it.should("do nothing when 'd' is pressed") {
            vimShortcutTranslator.keyPressed('d')

            verify { textChangeListenerMock wasNot Called }
        }

        it.should("remove one line when 'dd' is pressed") {
            repeat(2) { vimShortcutTranslator.keyPressed('d') }

            verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
        }

        it.should("remove two lines when 'dd' is pressed twice") {
            repeat(4) { vimShortcutTranslator.keyPressed('d') }

            verify(exactly = 2) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
        }
    }
})
