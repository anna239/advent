import kotlin.math.max

private class Run(val colors: Map<String, Int>) {
    fun isPossible(maxColors: Map<String, Int>): Boolean {
        return colors.all { (colour, size) ->
            size <= maxColors[colour]!!
        }
    }
}

private class Game(val id: Int, val runs: List<Run>) {
    fun getPower(): Int {
        val maxColors = hashMapOf<String, Int>()
        runs.forEach { run ->
            run.colors.forEach { (colour, size) ->
                maxColors[colour] = max(maxColors.getOrDefault(colour, 0), size)
            }
        }
        return maxColors.values.fold(1) { seed, it -> seed * it }
    }
}

fun main() {

    fun parseGame(str: String): Game {
        val (gm, runs) = str.split(":")
        val id = gm.split(" ")[1].toInt()
        val gameRuns = runs.split(";").map { run ->
            val colors = run.split(",").associate {
                val (size, color) = it.trim().split(" ")
                color to size.toInt()
            }
            Run(colors)
        }
        return Game(id, gameRuns)
    }

    fun part1(lines: List<String>): Int {
        val maxColors = mapOf("red" to 12, "green" to 13, "blue" to 14)

        fun isPossible(game: Game): Boolean {
            return game.runs.all { it.isPossible(maxColors) }
        }

        return lines.map { parseGame(it) }.filter { isPossible(it) }.fold(0) { seed, it -> seed + it.id }
    }

    fun part2(lines: List<String>): Int {
        return lines.map { parseGame(it).getPower() }.fold(0) { seed, it -> seed + it }
    }



    val input = readInput("Day02")
    part2(input).println()
}