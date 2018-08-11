package it.krzeminski.vimaal

import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify
import org.specnaz.kotlin.junit.SpecnazKotlinJUnit

class VimShortcutTranslatorSpec : SpecnazKotlinJUnit(VimShortcutTranslator::class.simpleName!!, { it ->
    lateinit var textChangeListenerMock: TextChangeListener
    lateinit var vimShortcutTranslator: VimShortcutTranslator

    fun keysPressed(keys: String) =
            keys.forEach { character -> vimShortcutTranslator.keyPressed(character) }

    it.beginsEach {
        textChangeListenerMock = spyk()
        vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock)
    }

    it.describes("deleting") {
        it.should("do nothing when 'd' is pressed") {
            keysPressed("d")

            verify { textChangeListenerMock wasNot Called }
        }

        it.should("remove one line when 'dd' is pressed") {
            keysPressed("dd")

            verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
        }

        it.should("remove two lines when 'dd' is pressed twice") {
            keysPressed("dddd")

            verify(exactly = 2) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
        }
    }
})
