sealed class Entity()

class Part(val x: Int, val y: Int, val symbol: Char): Entity() {
    override fun toString(): String {
        return symbol.toString()
    }
}

class Number(val number: Int): Entity() {
    override fun toString(): String {
        return number.toString()
    }
}

private fun readEngine(lines: List<String>): Array<Array<Entity?>> {
    val lineSize = lines[0].length
    val result = Array<Array<Entity?>>(lines.size) { Array(lineSize) { null } }
    lines.forEachIndexed { y, line ->
        var current = 0

        var digitStart = -1;
        var currentNumber = 0;


        while (current < lineSize) {
            val symbol = line[current]
            if (symbol.isDigit()) {
                if (digitStart < 0) {
                    digitStart = current
                }
                currentNumber = currentNumber * 10 + symbol.digitToInt()
            } else {
                if (digitStart >= 0) {
                    val number = Number(currentNumber)
                    for (x in digitStart..<current) {
                        result[y][x] = number
                    }
                    currentNumber = 0
                    digitStart = -1
                }
            }
            if (symbol != '.') {
                result[y][current] = Part(current, y, symbol)
            }
            current++
        }
        if (digitStart >= 0) {
            val number = Number(currentNumber)
            for (x in digitStart..<current) {
                result[y][x] = number
            }
        }
    }
    return result
}

fun part1(engine: Array<Array<Entity?>>): Int {
    var result = 0
    val xLength = engine[0].size
    val yLength = engine.size

    fun addValue(values: HashSet<Number>, x: Int, y: Int) {
        if (x < 0 || x >= xLength || y < 0 || y >= yLength) return
        (engine[y][x] as? Number)?.let { values.add(it) }
    }

    engine.forEach {
        it.forEach { entity ->
            if (entity is Part) {
                val values = hashSetOf<Number>()
                addValue(values, entity.x - 1, entity.y)
                addValue(values, entity.x, entity.y - 1)
                addValue(values, entity.x + 1, entity.y)
                addValue(values, entity.x, entity.y + 1)

                addValue(values, entity.x - 1, entity.y - 1)
                addValue(values, entity.x + 1, entity.y - 1)
                addValue(values, entity.x + 1, entity.y + 1)
                addValue(values, entity.x - 1, entity.y + 1)


                result += values.fold(0) {seed, it -> seed + it.number }
            }
        }
    }
    return result
}

fun part2(engine: Array<Array<Entity?>>): Int {
    var result = 0
    val xLength = engine[0].size
    val yLength = engine.size

    fun addValue(values: HashSet<Number>, x: Int, y: Int) {
        if (x < 0 || x >= xLength || y < 0 || y >= yLength) return
        (engine[y][x] as? Number)?.let { values.add(it) }
    }

    engine.forEach {
        it.forEach { entity ->
            if (entity is Part && entity.symbol == '*') {
                val values = hashSetOf<Number>()
                addValue(values, entity.x - 1, entity.y)
                addValue(values, entity.x, entity.y - 1)
                addValue(values, entity.x + 1, entity.y)
                addValue(values, entity.x, entity.y + 1)

                addValue(values, entity.x - 1, entity.y - 1)
                addValue(values, entity.x + 1, entity.y - 1)
                addValue(values, entity.x + 1, entity.y + 1)
                addValue(values, entity.x - 1, entity.y + 1)

                if (values.size == 2) {
                    result += values.fold(1) { seed, it -> seed * it.number }
                }
            }
        }
    }
    return result
}


fun main() {
    val input = readInput("Day03")
    val engine = readEngine(input)
    part2(engine).println()

}