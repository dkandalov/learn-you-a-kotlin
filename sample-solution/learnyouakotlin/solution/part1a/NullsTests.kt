package learnyouakotlin.solution.part1a

import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class NullsTests {

    @Test fun nulls() {
        val session: Session? = Sessions.sessionWithTitle("learn you a kotlin")
        assertEquals("for all the good it will do you", session!!.subtitle)
    }

    @Test fun elvis() {
        assertEquals("for all the good it will do you", subtitleOf(learnYouAKotlin))
        assertNull(subtitleOf(null))
    }

    @Test fun questionmark_thingy() {
        assertEquals("for all the good it will do you", learnYouAKotlin.subtitleOrPrompt())
        assertEquals("click to enter subtitle", refactoringToStreams.subtitleOrPrompt())
        assertEquals("click to enter subtitle", null.subtitleOrPrompt())
    }

}

private val someSlots = Slots(1, 1)
private val learnYouAKotlin = Session("Learn you a kotlin", "for all the good it will do you", someSlots)
private val refactoringToStreams = Session("Refactoring to Streams", null, someSlots)

fun subtitleOf(session: Session?): String? = session?.subtitle

fun Session?.subtitleOrPrompt() = subtitleOf(this) ?: "click to enter subtitle"

object Sessions {
    fun sessionWithTitle(s: String): Session? = s.toLowerCase().let {
        return when (it) {
            learnYouAKotlin.title.toLowerCase() -> learnYouAKotlin
            refactoringToStreams.title.toLowerCase() -> refactoringToStreams
            else -> null
        }
    }
}


