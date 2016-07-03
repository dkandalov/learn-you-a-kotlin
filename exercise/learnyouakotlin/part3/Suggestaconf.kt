package learnyouakotlin.part3

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.sun.net.httpserver.HttpServer
import learnyouakotlin.part1.Presenter
import learnyouakotlin.part1.Session
import learnyouakotlin.part2.array
import learnyouakotlin.part2.asJson
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_OK
import java.net.InetSocketAddress
import java.security.SecureRandom
import java.util.function.Function

private val no_subtitle = null

val mondaySessions = setOf(
    Session("Opening Plenary", no_subtitle, 1..1),

    Session("Play the GetKanban Game", no_subtitle, 2..6,
        Presenter("Mattia Battiston")),

    Session("Learn You A Kotlin", "For all the good it will do you", 2..3,
        Presenter("Nat Pryce"),
        Presenter("Duncan McGregor")),

    Session("Empathy Dojo", no_subtitle, 2..2,
        Presenter("Marina Haase")),

    Session("Agile: it's not just about the development team", no_subtitle, 2..2,
        Presenter("Andy Longshaw")),

    Session("Working Fearlessly with your Data", no_subtitle, 3..3,
        Presenter("Stephen Lewis"),
        Presenter("Ian Bell")),

    Session("10 Releases a Day and No Anguish", "Continuous Delivery for Humans", 3..3,
        Presenter("Douglas Squirrel")),

    Session("Neurodiversity", no_subtitle, 4..4,
        Presenter("Sallyann Freudenberg")),

    Session("Real World Big Data in Action", no_subtitle, 5..6,
        Presenter("Nick Rozanski"),
        Presenter("Eoin Woods"),
        Presenter("Chris Cooper-Bland")),

    Session("Education for Engineers", no_subtitle, 5..5,
        Presenter("Roberty Chatley")),

    Session("Agile in a Business-to-Business Relationship", no_subtitle, 6..6,
        Presenter("Camilla Brown")),

    Session("Pathfinding Peril", no_subtitle, 5..6,
        Presenter("Chris Parsons")),

    Session("Birds of a Feather Sessions", no_subtitle, 7..7)
)

fun main(args: Array<String>) {
    val rng = SecureRandom()
    val objectMapper = ObjectMapper().enable(INDENT_OUTPUT)

    val server: HttpServer = HttpServer.create().apply {
        bind(InetSocketAddress("0.0.0.0", 8910), 0)
        createContext("/monday") { exchange ->
            val schedule = rng.sample(allSchedules(mondaySessions))

            if (schedule != null) {
                exchange.responseHeaders["Content-Type"] = "application/json"
                exchange.sendResponseHeaders(HTTP_OK, 0)
                exchange.responseBody.use { out ->
                    objectMapper.writeValue(out, array(schedule, Function<Session, JsonNode> { it.asJson() }))
                }
            }
            else {
                exchange.sendResponseHeaders(HTTP_NOT_FOUND, 0)
            }

            exchange.close()
        }
    }

    server.start()
    println("http://localhost:8910/monday")
}

