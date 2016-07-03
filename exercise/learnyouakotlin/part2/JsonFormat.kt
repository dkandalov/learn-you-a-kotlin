package learnyouakotlin.part2

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import learnyouakotlin.part1.Presenter
import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots
import java.util.function.Function

fun Session.asJson(): JsonNode {
    return obj(
            prop("title", title),
            if (subtitle == null) null else prop("subtitle", subtitle),
            prop("slots", obj(
                    prop("first", slots!!.start),
                    prop("last", slots.endInclusive))),
            prop("presenters", array(presenters, Function<Presenter, JsonNode> { it.asJson() })))
}

fun JsonNode.asSession(): Session {
    val title = path("title").nonBlankText()
    val subtitle = path("subtitle").optionalNonBlankText()

    val authorsNode = path("presenters")
    val presenters = authorsNode.elements().asSequence().map{ it.asPresenter() }.toList()

    return Session(title, subtitle, Slots(1, 2), presenters)
}

private fun Presenter.asJson() = obj(prop("name", name))

private fun JsonNode.asPresenter() = Presenter(path("name").asText())

private fun JsonNode.optionalNonBlankText() = if (isMissingNode) null else nonBlankText()

private fun JsonNode.nonBlankText(): String {
    val text = asText()
    if (isNull || text == "") {
        throw JsonMappingException(null, "missing or empty text")
    } else {
        return text
    }
}
