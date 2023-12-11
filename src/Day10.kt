
private fun getChar(x: Int, y: Int, input: List<String>): Char {
    if (x < 0 || y < 0 || x >= input[0].length || y >= input.size) return '.'
    return input[y][x]
}

private fun getNextToS(x: Int, y: Int, input: List<String>): Pair<Int, Int> {
    val toCheck = listOf(
        (x - 1 to y) to setOf('-', 'L', 'F'),
        (x to y - 1) to setOf('|', '7', 'F'),
        (x + 1 to y) to setOf('-', 'J', '7'),
        (x to y + 1) to setOf('|', 'L', 'J')
    )
    for (opt in toCheck) {
        val (ox, oy) = opt.first
        if (opt.second.contains(getChar(ox, oy, input))) return ox to oy
    }
    throw RuntimeException()
}

private fun getS(x: Int, y: Int, input: List<String>): Pair<Pair<Int, Int>, Char> {
    val toCheck = listOf(
        (x - 1 to y) to setOf('-', 'L', 'F'),
        (x to y - 1) to setOf('|', '7', 'F'),
        (x + 1 to y) to setOf('-', 'J', '7'),
        (x to y + 1) to setOf('|', 'L', 'J')
    )
    val options = toCheck.filter { opt -> opt.second.contains(getChar(opt.first.first, opt.first.second, input)) }
        .map { it.first }.sortedBy { it.first }
    val left = options[0]
    val right = options[1]
    val char = if (left.first == right.first) {
        '|'
    } else if (left.second == right.second) {
        '-'
    } else if (left.first + 1 == x) {
        if (right.second + 1 == y) 'J' else '7'
    } else if (left.second + 1 == y) {
        'L'
    } else {
        'F'
    }
    return left to char
}

private fun part2(input: List<String>): Int {
    var sX = -1
    var sY = -1

    for (idx in input.indices) {
        val sIdx = input[idx].indexOf('S')
        if (sIdx != -1) {
            sX = sIdx
            sY = idx
            break
        }
    }
    var prevX = sX
    var prevY = sY
    val s = getS(sX, sY, input)
    var x = s.first.first
    var y = s.first.second
    val sChar = s.second
    val points = hashMapOf<Pair<Int, Int>, Char>()
    points.put(sX to sY, sChar)
    while (x != sX || y != sY) {
        val currentChar = getChar(x, y, input)
        points.put(x to y, currentChar)
        val next = when(currentChar) {
            '-' -> if (prevX < x) x + 1 to y else x - 1 to y
            '|' -> if (prevY < y) x to y + 1 else x to y - 1
            'F' -> if (prevY > y) x + 1 to y else x to y + 1
            'J' -> if (prevY < y) x - 1 to y else x to y - 1
            '7' -> if (prevX < x) x to y + 1 else x - 1 to y
            'L' -> if (prevX > x) x to y - 1 else x + 1 to y
            else -> throw RuntimeException()
        }
        prevX = x
        prevY = y
        x = next.first
        y = next.second
    }
    var space = 0
    for (y in input.indices) {
        var current = 0
        for (x in input[y].indices) {
            val point = points.get(x to y)
            if (point == null) {
                space += current
            } else {
                if (point == '|' || point == 'L' || point == 'J') {
                    current = if (current == 0) 1 else 0
                }
            }
        }
    }
    return space

}



private fun part1(input: List<String>): Int {
    var sX = -1
    var sY = -1

    for (idx in input.indices) {
        val sIdx = input[idx].indexOf('S')
        if (sIdx != -1) {
            sX = sIdx
            sY = idx
            break
        }
    }
    var count = 2
    var prevX = sX
    var prevY = sY
    var (x, y) = getNextToS(sX, sY, input)
    while (x != sX || y != sY) {
        val currentChar = getChar(x, y, input)
        val next = when(currentChar) {
            '-' -> if (prevX < x) x + 1 to y else x - 1 to y
            '|' -> if (prevY < y) x to y + 1 else x to y - 1
            'F' -> if (prevY > y) x + 1 to y else x to y + 1
            'J' -> if (prevY < y) x - 1 to y else x to y - 1
            '7' -> if (prevX < x) x to y + 1 else x - 1 to y
            'L' -> if (prevX > x) x to y - 1 else x + 1 to y
            else -> throw RuntimeException()
        }
        prevX = x
        prevY = y
        x = next.first
        y = next.second
        count++
    }
    return count / 2


}

fun main() {
    val input = readInput("Day10")
    part2(input).println()
}