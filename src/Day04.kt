import kotlin.math.pow

private fun part1(cards: Array<Card>): Int {
    return cards.fold(0) {seed, it ->
        seed + it.getValue()
    }
}

private fun part2(cards: Array<Card>): Int {
    val processedCards = Array<Int>(cards.size) { 0 }
    fun process(cards: Array<Card>, id: Int): Int {
        if (processedCards[id] != 0) return processedCards[id]
        val matching = cards[id].matchingNumbers
        var result = 1
        for (i in 1..matching) {
            result += process(cards, id + i)
        }
        processedCards[id] = result
        return result
    }

    return cards.foldIndexed(0) {idx, seed, it ->
        seed + process(cards, idx)
    }

}

class Card(val winning: List<Int>, val present: List<Int>) {
    fun getValue(): Int {
        val pow = matchingNumbers - 1
        if (pow < 0) return 0
        return 2.0.pow(pow.toDouble()).toInt()
    }

    val matchingNumbers by lazy(LazyThreadSafetyMode.NONE) { present.intersect(winning.toSet()).size }
}

private fun parse(lines: List<String>): Array<Card> {
    return lines.map { line ->
        val (winning, present) = line.split(":")[1].split("|")
        Card(toInts(winning), toInts(present))
    }.toTypedArray()
}

private fun toInts(winning: String): List<Int> {
    return winning.trim().split(" ").mapNotNull { it.trim().takeIf { it.isNotBlank() }?.toInt() }
}

fun main() {
    val input = readInput("Day04")
    val cards = parse(input)
    part2(cards).println()
}