class Hand(val cards: List<Int>, val bid: Int) : Comparable<Hand> {
    val handType: Int = getType()

    private fun getType(): Int {
        val map = hashMapOf<Int, Int>()
        cards.forEach {
            if (it != 1) {
                map[it] = map.getOrDefault(it, 0) + 1
            }
        }
        if (map.keys.size == 0) return 7
        if (map.keys.size == 1) return 7
        if (map.keys.size == 2) {
            if (map.values.any { it == 1 }) return 6
            return 5
        }
        if (map.keys.size == 3) {
            if (map.values.filter { it == 1 }.size >= 2) return 4
            return 3
        }
        if (map.keys.size == 4) return 2
        return 1
    }

    override fun compareTo(other: Hand): Int {
        if (handType < other.handType) return -1
        if (handType > other.handType) return 1
        for (i in 0..4) {
            if (cards[i] < other.cards[i]) return -1
            if (cards[i] > other.cards[i]) return 1
        }
        return 0
    }
}

private fun parse(line: String): Hand {
    fun cardToNumber(char: Char): Int {
        return when (char) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'T' -> 10
            'J' -> 1
            else -> char.digitToInt()
        }
    }

    val (cards, bid) = line.split(" ")
    return Hand(cards.map { cardToNumber(it) }, bid.toInt())

}

private fun part1(lines: List<String>): Int {
    val hands = lines.map { parse(it) }
    return hands.sorted().foldIndexed(0) { idx , seed, it ->
        seed + (idx + 1) * it.bid
    }
}

fun main() {
    val input = readInput("Day07")
    part1(input).println()
}