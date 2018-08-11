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
        it.describes("with 'd' command") {
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

            it.should("repeat deleting 3 times when '3dd' is pressed") {
                keysPressed("3dd")

                verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(quantity = 3) }
            }

            it.should("repeat deleting 123 times when '123dd' is pressed") {
                keysPressed("123dd")

                verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(quantity = 123) }
            }

            it.should("delete once when '0dd' is pressed") {
                keysPressed("0dd")

                verify(exactly = 1) { textChangeListenerMock.onLinesRemoved(quantity = 1) }
            }
        }

        it.describes("with 'x' command") {
            it.should("remove one character when 'x' is pressed") {
                keysPressed("x")

                verify(exactly = 1) { textChangeListenerMock.onCharactersRemoved(quantity = 1) }
            }

            it.should("remove 3 characters when 'xxx' is pressed") {
                keysPressed("xxx")

                verify(exactly = 3) { textChangeListenerMock.onCharactersRemoved(quantity = 1) }
            }

            it.should("repeat deleting 3 times when '3x' is pressed") {
                keysPressed("3x")

                verify(exactly = 1) { textChangeListenerMock.onCharactersRemoved(quantity = 3) }
            }

            it.should("repeat deleting 123 times when '123x' is pressed") {
                keysPressed("123x")

                verify(exactly = 1) { textChangeListenerMock.onCharactersRemoved(quantity = 123) }
            }
        }
    }
})
