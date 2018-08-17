package it.krzeminski.vimaal

import io.mockk.Called
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.specnaz.kotlin.junit.SpecnazKotlinJUnit

class VimShortcutTranslatorSpec : SpecnazKotlinJUnit(VimShortcutTranslator::class.simpleName!!, { it ->
    lateinit var textChangeListenerMock: TextChangeListener
    lateinit var navigationListenerMock: NavigationListener
    lateinit var vimShortcutTranslator: VimShortcutTranslator

    fun keysPressed(keys: String) =
            keys.forEach { character -> vimShortcutTranslator.keyPressed(character) }

    it.beginsEach {
        textChangeListenerMock = spyk()
        navigationListenerMock = spyk()
        vimShortcutTranslator = VimShortcutTranslator(textChangeListenerMock, navigationListenerMock)
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

            it.should("move to the beginning of the line and delete one line when '0dd' is pressed") {
                keysPressed("0dd")

                verify(exactly = 1) {
                    navigationListenerMock.goToBeginningOfLine()
                    textChangeListenerMock.onLinesRemoved(quantity = 1)
                }
                verifyOrder {
                    navigationListenerMock.goToBeginningOfLine()
                    textChangeListenerMock.onLinesRemoved(quantity = 1)
                }
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

    it.describes("navigation") {
        it.describes("moving by lines") {
            it.should("move to the first line when 'gg' pressed") {
                keysPressed("gg")

                verify(exactly = 1) {
                    navigationListenerMock.goToLine(LineReference.Number(1))
                }
            }

            it.should("move to the 123th line when '123gg' pressed") {
                keysPressed("123gg")

                verify(exactly = 1) {
                    navigationListenerMock.goToLine(LineReference.Number(123))
                }
            }

            it.should("move to the last line when 'G' pressed") {
                keysPressed("G")

                verify(exactly = 1) {
                    navigationListenerMock.goToLine(LineReference.Last)
                }
            }

            it.should("move to the 123th line when '123G' pressed") {
                keysPressed("123G")

                verify(exactly = 1) {
                    navigationListenerMock.goToLine(LineReference.Number(123))
                }
            }
        }

        it.describes("moving by characters") {
            listOf(
                    Triple("h", "left", Direction.LEFT),
                    Triple("j", "down", Direction.DOWN),
                    Triple("k", "up", Direction.UP),
                    Triple("l", "right", Direction.RIGHT)
            ).forEach { case ->
                it.should("move one character to the ${case.second} when '${case.first}' pressed") {
                    keysPressed(case.first)

                    verify(exactly = 1) {
                        navigationListenerMock.moveByCharacters(direction = case.third, distance = 1)
                    }
                }

                it.should("move 3 characters to the ${case.second} when '3${case.first}' pressed") {
                    keysPressed("3${case.first}")

                    verify(exactly = 1) {
                        navigationListenerMock.moveByCharacters(direction = case.third, distance = 3)
                    }
                }

                it.should("move 123 characters to the ${case.second} when '123${case.first}' pressed") {
                    keysPressed("123${case.first}")

                    verify(exactly = 1) {
                        navigationListenerMock.moveByCharacters(direction = case.third, distance = 123)
                    }
                }
            }
        }

        it.should("move to the beginning of the line when '0' pressed") {
            keysPressed("0")

            verify(exactly = 1) { navigationListenerMock.goToBeginningOfLine() }
        }
    }
})
