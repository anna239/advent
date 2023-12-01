fun main() {

    fun extractNumber(str: String): Int {
        return str.first { it.isDigit() }.digitToInt() * 10 + str.last { it.isDigit() }.digitToInt()
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { seed, it ->
            seed + extractNumber(it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    part1(testInput).println()

    val input = readInput("Day01")
    part1(input).println()
}
