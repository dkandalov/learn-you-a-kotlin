package learnyouakotlin.part2

import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import learnyouakotlin.part1.Presenter
import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Rule
import org.junit.Test

class JsonFormatTests {
    @get:Rule val approval = ApprovalsRule.fileSystemRule("exercise")!!

    @Test fun `session to json`() {
        val session = Session(
                "Learn You a Kotlin For All The Good It Will Do You",
                null,
                Slots(1, 2),
                Presenter("Duncan McGregor"),
                Presenter("Nat Pryce"))

        val json = session.asJson()

        approval.assertApproved(json, { it.asStableJsonString() })
    }

    @Test fun `session with subtitle to json`() {
        val session = Session(
                "Scrapheap Challenge",
                "A Workshop in Postmodern Programming",
                Slots(3, 3),
                Presenter("Ivan Moore"))

        val json = session.asJson()

        approval.assertApproved(json, { it.asStableJsonString() })
    }

    @Test fun `session to and from json`() {
        val original = Session(
                "Working Effectively with Legacy Tests",
                null,
                Slots(1, 2),
                Presenter("Nat Pryce"),
                Presenter("Duncan McGregor"))

        val parsed = original.asJson().asSession()

        assertThat(parsed, equalTo(original))
    }
}
