private fun part1(input: Array<Array<Char>>): Int {
    tiltNorth(input)
    return getLoad(input)
}

private fun getLoad(input: Array<Array<Char>>): Int {
    var result = 0
    for (y in input.indices) {
        result += input[y].count { it == 'O' } * (input.size - y)
    }
    return result
}

private fun tiltNorth(input: Array<Array<Char>>) {
    for (x in input[0].indices) {
        var nextStonePos = 0
        for (y in input.indices) {
            val obj = input[y][x]
            if (obj == 'O') {
                input[y][x] = '.'
                input[nextStonePos][x] = 'O'
                nextStonePos++
            } else if (obj == '#') {
                nextStonePos = y + 1
            }
        }
    }
}

private fun tiltSouth(input: Array<Array<Char>>) {
    for (x in input[0].indices) {
        var nextStonePos = input.size - 1
        for (y in input.indices.reversed()) {
            val obj = input[y][x]
            if (obj == 'O') {
                input[y][x] = '.'
                input[nextStonePos][x] = 'O'
                nextStonePos--
            } else if (obj == '#') {
                nextStonePos = y - 1
            }
        }
    }
}

private fun tiltWest(input: Array<Array<Char>>) {
    for (y in input.indices) {
        var nextStonePos = 0
        for (x in input[0].indices) {
            val obj = input[y][x]
            if (obj == 'O') {
                input[y][x] = '.'
                input[y][nextStonePos] = 'O'
                nextStonePos++
            } else if (obj == '#') {
                nextStonePos = x + 1
            }
        }
    }
}

private fun tiltEast(input: Array<Array<Char>>) {
    for (y in input.indices) {
        var nextStonePos = input[0].size - 1
        for (x in input[0].indices.reversed()) {
            val obj = input[y][x]
            if (obj == 'O') {
                input[y][x] = '.'
                input[y][nextStonePos] = 'O'
                nextStonePos--
            } else if (obj == '#') {
                nextStonePos = x - 1
            }
        }
    }
}

private fun toString(input: Array<Array<Char>>): String {
    return input.joinToString("") { it.joinToString("") }
}

private fun part2(input: Array<Array<Char>>): Int {
    val steps = hashMapOf<String, Int>()
    val all = 1000000000
    for (i in 0..<all) {
//        if (i % 1000 == 0) {
//            println(i)
//        }
        tiltNorth(input)
        tiltWest(input)
        tiltSouth(input)
        tiltEast(input)
        val result = toString(input)
        if (steps.containsKey(result)) {
            val prev = steps[result]!!
            val period = i - prev
            val toGo = (all - i - 1) % period
            repeat(toGo) {
                tiltNorth(input)
                tiltWest(input)
                tiltSouth(input)
                tiltEast(input)
            }
            break
        } else {
            steps[result] = i
        }
    }
    return getLoad(input)
}


fun main() {
    val input = readInput("Day14").map { it.toCharArray().toTypedArray() }.toTypedArray()
    part2(input).println()
}