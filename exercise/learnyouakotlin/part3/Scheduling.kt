package learnyouakotlin.part3

import learnyouakotlin.part1.Session
import java.util.*

fun allSchedules(sessions: Iterable<Session>): Sequence<List<Session>> {
    fun minSlot(sessions: Iterable<Session>) = sessions.minBy { it.slots.start }?.slots?.start
    fun maxSlot(sessions: Iterable<Session>) = sessions.maxBy { it.slots.endInclusive }?.slots?.endInclusive
    fun allSchedules(sessions: Iterable<Session>, slot: Int, endSlot: Int): Sequence<List<Session>> {
        if (slot > endSlot) return sequenceOf(listOf<Session>())
        return sessions
            .filter { it.slots.start == slot }
            .flatMap { session ->
                val updatedSessions = sessions.filter { it.slots.start > session.slots.endInclusive }
                val nextSlot = minSlot(updatedSessions) ?: slot + 1
                allSchedules(updatedSessions, nextSlot, endSlot).map{ listOf(session) + it }.toList()
            }.asSequence()
    }

    return allSchedules(sessions, minSlot(sessions)!!, maxSlot(sessions)!!)
}

fun <T> Random.sample(sequence : Sequence<T>) : T? = sequence.toList().let { it[nextInt(it.size)] }


