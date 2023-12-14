import kotlin.math.min

private fun getDiff(left: Int, right: Int): Int {
    val p = left xor right
    if (p == 0) {
        return 0
    }
    if (p and (p - 1) == 0) {
        return 1
    }
    return 2
}

private fun calculateBit(input: List<Int>): Int {
    val length = input.size
    for (i in 1..<length) {
        val size = min(i, length - i)
        var diff = 0
        for (idx in 0..<size) {
            diff += getDiff(input[i - idx - 1], input[i + idx])
            if (diff > 1) {
                break
            }
        }
        if (diff == 1) {
            return i
        }
    }
    return 0
}

private fun strToInt(string: String): Int {
    return string.fold(0) { seed, it ->
        seed * 2 + (if (it == '#') 1 else 0)
    }
}

private fun getForPatternBit(input: List<String>): Int {
    val result = calculateBit(input.map { strToInt(it) })
    if (result != 0) return result * 100
    val newInput = ArrayList<String>(input.size)
    for (i in input[0].indices) {
        newInput.add(input.map { it[i] }.joinToString(""))
    }
    return calculateBit(newInput.map { strToInt(it) })
}



private fun calculate(input: List<String>): Int {
    val length = input.size
    for (i in 1..<length) {
        val size = min(i, length - i)
        var problem = false
        for (idx in 0..<size) {
            if (input[i - idx - 1] != input[i + idx]) {
                problem = true
                break
            }
        }
        if (!problem) {
            return i
        }
    }
    return 0
}

private fun getForPattern(input: List<String>): Int {
    val result = calculate(input)
    if (result != 0) return result * 100
    val newInput = ArrayList<String>(input.size)
    for (i in input[0].indices) {
        newInput.add(input.map { it[i] }.joinToString(""))
    }
    return calculate(newInput)
}


private fun part1(input: List<String>): Long {
    var data = input
    var resut = 0L
    while (data.isNotEmpty()) {
        var idx = data.indexOfFirst { it.isBlank() }
        if (idx == -1) {
            idx = data.size
        }
        resut += getForPattern(data.subList(0, idx))
        data = data.drop(idx + 1)
    }
    return resut

}

private fun part2(input: List<String>): Long {
    var data = input
    var resut = 0L
    while (data.isNotEmpty()) {
        var idx = data.indexOfFirst { it.isBlank() }
        if (idx == -1) {
            idx = data.size
        }
        resut += getForPatternBit(data.subList(0, idx))
        data = data.drop(idx + 1)
    }
    return resut

}

fun main() {
    val input = readInput("Day13")
    part2(input).println()
}