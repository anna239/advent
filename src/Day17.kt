import java.util.PriorityQueue
import kotlin.math.min

private data class Data(val x: Int, val y: Int, var minValue: Int, var prevX: Int, val prevY: Int, var lineLength: Int)
private data class Edge(val x: Int, val y: Int, val value: Int)
private data class Visited(val x: Int, val y: Int, val line: Int, val dir: Int)

private fun part1(input: Array<Array<Int>>): Int {
    fun getEdges(data: Data): List<Edge> {
        val pairs = listOf(
            data.x - 1 to data.y,
            data.x + 1 to data.y,
            data.x to data.y - 1,
            data.x to data.y + 1
        ).filter { (x, y) ->
            x >= 0 && y >= 0 && !(x == data.prevX && y == data.prevY) && x < input[0].size && y < input.size
        }
        return pairs.map { (x, y) -> Edge(x, y, input[y][x]) }
    }

    val queue = PriorityQueue<Data> { o1, o2 -> o1.minValue.compareTo(o2.minValue) }
    val processed = HashMap<Visited, Int>()
    val start = Data(0, 0, 0, -1, -1, 1)
    queue.offer(start)
    var foundWeight = Int.MAX_VALUE
    while (queue.isNotEmpty()) {
        val u = queue.poll()
        if (u.minValue > foundWeight) continue
        if (isEndNode(u.x, u.y, input)) continue
        val edges = getEdges(u)
        edges.forEach { e ->
            val nextWeight = u.minValue + e.value
            val x = e.x
            val y = e.y
            var d: Data? = null
            if (u.prevX == u.x && u.x == x || u.prevY == u.y && u.y == y) {
                if (u.lineLength < 3) {
                    d = Data(x, y, nextWeight, u.x, u.y, u.lineLength + 1)
                }
            } else {
                d = Data(x, y, nextWeight, u.x, u.y, 1)
            }
            if (d != null) {
                if (isEndNode(x, y, input)) {
                    foundWeight = min(nextWeight, foundWeight)
                }
                val v = Visited(x, y, d.lineLength, (u.x - x) * 10 + (u.y - y))
                if (!processed.containsKey(v) || processed[v]!! > nextWeight) {
                    processed[v] = nextWeight
                    queue.offer(d)
//                    data[y][x] = d
                }
            }
        }
    }
    return foundWeight

}
private fun part2(input: Array<Array<Int>>): Int {
    fun getEdges(data: Data): List<Edge> {
        val pairs = listOf(
            data.x - 1 to data.y,
            data.x + 1 to data.y,
            data.x to data.y - 1,
            data.x to data.y + 1
        ).filter { (x, y) ->
            x >= 0 && y >= 0 && !(x == data.prevX && y == data.prevY) && x < input[0].size && y < input.size
        }
        return pairs.map { (x, y) -> Edge(x, y, input[y][x]) }
    }

    val queue = PriorityQueue<Data> { o1, o2 -> o1.minValue.compareTo(o2.minValue) }
    val processed = HashMap<Visited, Int>()
    val start = Data(0, 0, 0, -1, -1, 1)
    queue.offer(start)
    var foundWeight = Int.MAX_VALUE
    while (queue.isNotEmpty()) {
        val u = queue.poll()
        if (u.minValue > foundWeight) continue
        if (isEndNode(u.x, u.y, input)) continue
        val edges = getEdges(u)
        edges.forEach { e ->
            val nextWeight = u.minValue + e.value
            val x = e.x
            val y = e.y
            var d: Data? = null
            if (u.prevX == u.x && u.x == x || u.prevY == u.y && u.y == y) {
                if (u.lineLength < 10) {
                    d = Data(x, y, nextWeight, u.x, u.y, u.lineLength + 1)
                }
            } else if (u.lineLength >= 4 || u.x == 0 && u.y == 0) {
                d = Data(x, y, nextWeight, u.x, u.y, 1)
            }
            if (d != null) {
                if (isEndNode(x, y, input)) {
                    foundWeight = min(nextWeight, foundWeight)
                }
                val v = Visited(x, y, d.lineLength, (u.x - x) * 10 + (u.y - y))
                if (!processed.containsKey(v) || processed[v]!! > nextWeight) {
                    processed[v] = nextWeight
                    queue.offer(d)
//                    data[y][x] = d
                }
            }
        }
    }
    return foundWeight

}

private fun isEndNode(x: Int, y: Int, input: Array<Array<Int>>) =
    x == input[0].size - 1 && y == input.size - 1

fun main() {
    val input = readInput("Day17")
    val inp = input.map { line -> line.map { it.digitToInt() }.toTypedArray() }.toTypedArray()
    part2(inp).println()


}