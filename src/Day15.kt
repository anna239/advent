private fun hash(str: String): Int {
    return str.fold(0) {seed, it ->
        (seed + it.code) * 17 % 256
    }
}

data class Lens(val id: String, var focus: Int)

private fun part1(input: List<String>): Int {
    return input[0].splitToSequence(',').fold(0) {seed, it -> seed + hash(it)}
}

private fun part2(input: List<String>): Int {
    val lenses = Array<MutableList<Lens>>(256) { arrayListOf() }
    input[0].splitToSequence(',').forEach { inst ->
        if (inst.contains("=")) {
            val (id, focus) = inst.split("=")
            val idx = hash(id)
            val found = lenses[idx].firstOrNull { it.id == id }
            if (found != null) {
                found.focus = focus.toInt()
            } else {
                lenses[idx].add(Lens(id, focus.toInt()))
            }
        } else {
            val id = inst.substring(0, inst.length - 1)
            lenses[hash(id)].removeIf { it.id == id }
        }
    }
    var result = 0
    for (i in lenses.indices) {
        val content = lenses[i]
        content.forEachIndexed { index, lens ->
            result += (1 + i) * (1 + index) * lens.focus
        }
    }
    return result
}

fun main() {
    val input = readInput("Day15")
    part2(input).println()
}