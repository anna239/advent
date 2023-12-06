import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sqrt

class Race(val time: Int, val dist: Int)

private fun getOptionsNumber(t: Long, x: Long): Int {
    val d = sqrt((t * t - 4 * x).toDouble())

    var x2 = (t + d) / 2
    var x1 = (t - d) / 2
    if (floor(x2) == x2) {
        x2 -= 0.1
    }
    if (floor(x1) == x1) {
        x1 += 0.1
    }
    val x2Floor = floor(x2).toInt()
    val x1Ceil = max(0, ceil(x1).toInt())
    return x2Floor - x1Ceil + 1
}

private fun part1(races: List<Race>): Int {

    return races.fold(1) { seed, it ->
        seed * getOptionsNumber(it.time.toLong(), it.dist.toLong())
    }

}

private fun parseInput1(input: List<String>): List<Race> {
    val times = input[0].split(":")[1].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    val dists = input[1].split(":")[1].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    return times.mapIndexed { idx, t ->
        Race(t, dists[idx])
    }
}

private fun parseInput2(input: List<String>): Pair<Long, Long> {
    val time = input[0].split(":")[1].replace(" ", "").toLong()
    val dist = input[1].split(":")[1].replace(" ", "").toLong()
    return time to dist
}

fun main() {
    val input = readInput("Day06")
//    part1(parseInput1(input)).println()

    val (time, dist) = parseInput2(input)
    getOptionsNumber(time, dist).println()
}