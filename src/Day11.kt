import kotlin.math.abs

private class Galaxy(var x: Long, val y: Long)

private fun part1(input: List<String>): Long {
    val galaxies = arrayListOf<Galaxy>()
    var actualY = 0L
    val cols = input[0].indices.toHashSet()
    for (y in input.indices) {
        val line = input[y]
        var galaxyFount = false
        for (x in line.indices) {
            if (line[x] == '#') {
                galaxies.add(Galaxy(x.toLong(), actualY))
                galaxyFount = true
                cols.remove(x)
            }
        }
        if (!galaxyFount) {
            actualY += 1000000 - 1
        }
        actualY++
    }
    val sortedCols = cols.sorted()
    galaxies.forEach {
        var indexOfFirst = sortedCols.indexOfFirst { col -> col > it.x }
        if (indexOfFirst == -1) {
            indexOfFirst = sortedCols.size
        }
        it.x += indexOfFirst * (1000000 - 1)
    }
    var sum = 0L
    for (i in galaxies.indices) {
        for (j in i + 1..<galaxies.size) {
            sum += abs(galaxies[i].x - galaxies[j].x) + abs(galaxies[i].y - galaxies[j].y)
        }
    }
    return sum
}

fun main() {
    val input = readInput("Day11")
    part1(input).println()
}