import java.util.LinkedList

private fun isAllZeroes(source: Array<Int>, len: Int): Boolean {
    for (i in 0..<len) {
        if (source[i] != 0) return false
    }
    return true
}

private fun calculate2(numbers: Array<Int>): Int {
    val firstNumbers = LinkedList<Int>()
    var source = numbers
    var target = Array<Int>(numbers.size) { 0 }
    var currentLen = source.size
    while (!isAllZeroes(source, currentLen)) {
        firstNumbers.add(source[0])
        currentLen--
        for (i in 0..<currentLen) {
            target[i] = source[i + 1] - source[i]
        }
        val tmp = source
        source = target
        target = tmp
    }
    var newValue = 0
    firstNumbers.reversed().forEach {
        newValue = it - newValue
    }

    return newValue
}


private fun calculate1(numbers: Array<Int>): Int {
    val endNumbers = LinkedList<Int>()
    var source = numbers
    var target = Array<Int>(numbers.size) { 0 }
    var currentLen = source.size
    while (!isAllZeroes(source, currentLen)) {
        endNumbers.add(source[currentLen - 1])
        currentLen--
        for (i in 0..<currentLen) {
            target[i] = source[i + 1] - source[i]
        }
        val tmp = source
        source = target
        target = tmp
    }
    return endNumbers.fold(0) { seed, it -> seed + it }
}

private fun parse(input: List<String>): List<Array<Int>> {
    return input.map { line ->
        line.split(" ").map { it.toInt() }.toTypedArray()
    }
}

fun main() {
    val input = readInput("Day09")
    parse(input).fold(0) { seed, it ->
        seed + calculate2(it)
    }.println()
}