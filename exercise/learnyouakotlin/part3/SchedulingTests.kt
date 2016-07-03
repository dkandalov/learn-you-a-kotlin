package learnyouakotlin.part3

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots
import org.junit.Test

class ScheduleTests {
    @Test fun `single track`() {
        val s1 = Session("1", null, Slots(1, 1))
        val s2 = Session("2", null, Slots(2, 3))
        val s3 = Session("3", null, Slots(4, 4))

        val sessions = setOf(s1, s2, s3)

        assertThat(allSchedules(sessions).toList(), equalTo(listOf(listOf(s1, s2, s3))))
    }

    @Test fun `two tracks and plenaries`() {
        val p1 = Session("P1", null, Slots(1, 1))
        val a2 = Session("A2", null, Slots(2, 3))
        val b2 = Session("B2", null, Slots(2, 2))
        val b3 = Session("B3", null, Slots(3, 3))
        val p4 = Session("P4", null, Slots(4, 4))

        val sessions = setOf(p1, a2, b2, b3, p4)

        assertThat(allSchedules(sessions).toList(), equalTo(listOf(
            listOf(p1, a2, p4),
            listOf(p1, b2, b3, p4))))
    }

    @Test fun `empty slot in one track`() {
        val a1 = Session("A1", null, Slots(1, 3))
        val b1 = Session("B1", null, Slots(1, 2))
        val p4 = Session("P4", null, Slots(4, 4))

        val sessions = setOf(a1, b1, p4)

        assertThat(allSchedules(sessions).toList(), equalTo(listOf(
            listOf(a1, p4),
            listOf(b1, p4))))
    }
}
