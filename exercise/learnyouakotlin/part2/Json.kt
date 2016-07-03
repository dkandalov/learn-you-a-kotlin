package learnyouakotlin.part2

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS
import com.fasterxml.jackson.databind.node.*
import java.util.*
import java.util.function.Function

private val nodes = JsonNodeFactory.instance
private val stableMapper = ObjectMapper().enable(INDENT_OUTPUT, ORDER_MAP_ENTRIES_BY_KEYS)

fun prop(name: String, textValue: String) = prop(name, TextNode(textValue))

fun prop(name: String, intValue: Int) = prop(name, IntNode(intValue))

fun prop(name: String, value: JsonNode): Map.Entry<String, JsonNode> = ImmutableMapEntry(name, value)

@SafeVarargs
fun obj(vararg props: Map.Entry<String, JsonNode>?) = obj(props.toList())

fun obj(props: Iterable<Map.Entry<String, JsonNode>?>): ObjectNode {
    val objectNode = ObjectNode(null)
    props.forEach { p ->
        if (p != null) {
            objectNode.set(p.key, p.value)
        }
    }
    return objectNode
}

fun array(elements: Iterable<JsonNode>): ArrayNode {
    val array = ArrayNode(null)
    elements.forEach{ array.add(it) }
    return array
}

fun <T> array(elements: List<T?>, fn: Function<T, JsonNode>): ArrayNode {
    return array(elements.map{fn.apply(it)})
}

fun JsonNode.asStableJsonString(): String {
    try {
        return stableMapper.writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        throw IllegalArgumentException("failed to convert JsonNode to JSON string", e)
    }
}

private class ImmutableMapEntry<K, V>(key: K, value: V) : AbstractMap.SimpleEntry<K, V>(key, value) {
    override fun setValue(newValue: V): V {
        throw UnsupportedOperationException("unmodifiable")
    }
}
