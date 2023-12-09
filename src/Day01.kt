fun main() {

    fun extractNumber(str: String): Int {
        return str.first { it.isDigit() }.digitToInt() * 10 + str.last { it.isDigit() }.digitToInt()
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { seed, it ->
            seed + extractNumber(it)
        }
    }

    val nameToNumber = listOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine"
    ).mapIndexed { index, s -> s to index + 1 }.toMap()

    fun buildTree(nameToNumber: Map<String, Int>): OldNode {
        val result = OldNode(0)
        nameToNumber.forEach { (key, value) ->
            var current = result
            key.asIterable().iterator().forEach {
                current = current.children.getOrPut(it) { OldNode(current.depth + 1) }
            }
            current.number = value
        }
        return result
    }

    fun extractNumber(s: String, tree: OldNode): Int {
        var current = tree
        var id = 0
        while (true) {
            val next = s[id]
            if (next.isDigit()) return next.digitToInt()
            val node = current.children[next]
            if (node != null) {
                current = node
                val number = current.number
                id++
                if (number != null) return number
            } else {
                id -= current.depth
                current = tree
                id++
            }
        }
    }

    fun part2(input: List<String>): Int {
        val forward = buildTree(nameToNumber)
        val backward = buildTree(nameToNumber.mapKeys { it.key.reversed() })
        return input.fold(0) { seed, it ->
            val first = extractNumber(it, forward)
            val second = extractNumber(it.reversed(), backward)
            val res = 10 * first + second
            res.println()
            seed + res
        }
    }

    // test if implementation meets criteria from the description, like:
//    val input = readInput("Day01_test")
//    part2(testInput).println()

    val input = readInput("Day01")
    part2(input).println()
}

private class OldNode(val depth: Int, var number: Int? = null, val children: MutableMap<Char, OldNode> = hashMapOf())

