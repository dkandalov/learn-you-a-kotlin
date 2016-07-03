package learnyouakotlin.part1


data class Session(val title: String, val subtitle: String?, val slots: Slots, val presenters: List<Presenter>) {
    constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter)
        : this(title, subtitle, slots, presenters.toList())
    constructor(title: String, subtitle: String?, slots: ClosedRange<Int>, vararg presenters: Presenter)
        : this(title, subtitle, Slots(slots.start, slots.endInclusive), presenters.toList())
}

fun Session.withSubtitle(newSubtitle: String?) = copy(subtitle = newSubtitle)

fun Session.withTitle(newTitle: String) = copy(title = newTitle)

fun Session.withPresenters(newLineUp: List<Presenter>) = copy(presenters = newLineUp)
