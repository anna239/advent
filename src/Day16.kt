
private enum class Direction { LEFT, RIGHT, UP, DOWN }
private data class Light(val x: Int, val y: Int, val direction: Direction) {
    fun move(direction: Direction): Light {
        var newX = x
        var newY = y
        when(direction) {
            Direction.LEFT -> newX--
            Direction.RIGHT -> newX++
            Direction.UP -> newY--
            Direction.DOWN -> newY++
        }
        return Light(newX, newY, direction)
    }
}

private fun process(input: List<String>, light: Light, processed: MutableSet<Light>, energized: MutableSet<Pair<Int, Int>>) {
    if (processed.contains(light)) return
    if (light.y >= input.size || light.x >= input[0].length || light.y < 0 || light.x < 0) return
    energized.add(light.x to light.y)
    processed.add(light)
    val current = input[light.y][light.x]
    if (current == '.') {
        process(input, light.move(light.direction), processed, energized)
    } else if (current == '-') {
        if (light.direction == Direction.LEFT || light.direction == Direction.RIGHT) {
            process(input, light.move(light.direction), processed, energized)
        } else {
            process(input, light.move(Direction.LEFT), processed, energized)
            process(input, light.move(Direction.RIGHT), processed, energized)
        }
    } else if (current == '|') {
        if (light.direction == Direction.DOWN || light.direction == Direction.UP) {
            process(input, light.move(light.direction), processed, energized)
        } else {
            process(input, light.move(Direction.UP), processed, energized)
            process(input, light.move(Direction.DOWN), processed, energized)
        }
    } else if (current == '\\') {
        val newDir = when(light.direction) {
            Direction.LEFT -> Direction.UP
            Direction.RIGHT -> Direction.DOWN
            Direction.UP -> Direction.LEFT
            Direction.DOWN -> Direction.RIGHT
        }
        process(input, light.move(newDir), processed, energized)
    } else { // /
        val newDir = when(light.direction) {
            Direction.LEFT -> Direction.DOWN
            Direction.RIGHT -> Direction.UP
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
        }
        process(input, light.move(newDir), processed, energized)
    }
}

private fun part1(input: List<String>): Int {
    val processed = hashSetOf<Light>()
    val energized = hashSetOf<Pair<Int, Int>>()
    process(input, Light(0, 0, Direction.RIGHT), processed, energized)
    return energized.size
}

private fun part2(input: List<String>): Int {
    val maxY = input.size - 1
    val maxX = input[0].length - 1
    val allOption = listOf(
        Light(0, 0, Direction.RIGHT),
        Light(0, 0, Direction.DOWN),
        Light(0, maxY, Direction.RIGHT),
        Light(0, maxY, Direction.UP),
        Light(maxX, 0, Direction.LEFT),
        Light(maxX, 0, Direction.DOWN),
        Light(maxX, maxY, Direction.LEFT),
        Light(maxX, maxY, Direction.UP)
    ).plus((1..<maxX).flatMap { listOf(Light(it, 0, Direction.DOWN), Light(it, maxY, Direction.UP)) })
        .plus((1..<maxY).flatMap { listOf(Light(0, it, Direction.RIGHT), Light(maxX, it, Direction.LEFT)) })
    return allOption.maxOf {
        val processed = hashSetOf<Light>()
        val energized = hashSetOf<Pair<Int, Int>>()
        process(input, it, processed, energized)
        energized.size
    }
}

fun main() {
    val input = readInput("Day16")
    part2(input).println()
}