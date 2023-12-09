
private class Node(val id: String, val left: String, val right: String, directionsSize: Int = 0) {
    val reachesEndNodeIn: Array<Pair<Int, String>?> = Array(directionsSize) { null }

    override fun toString(): String {
        return id
    }
}

private fun getOrCalculate(nodes: Map<String, Node>, node: String, counter: Long, directions: String): Pair<Int, String> {
    val idx = (counter % directions.length).toInt()
    val existing = nodes[node]!!.reachesEndNodeIn[idx]
    if (existing != null) {
        return existing
    }
    val next = when(directions[idx]) {
        'R' -> nodes[node]!!.right
        'L' -> nodes[node]!!.left
        else -> throw RuntimeException()
    }
    val (dist, end) = if (next.endsWith("Z")) {
        0 to next
    } else {
        getOrCalculate(nodes, next, counter + 1, directions)
    }
    nodes[node]!!.reachesEndNodeIn[idx] = (dist + 1) to end
    return (dist + 1) to end
}
// 11795205644011
private fun test(directions: String, nodes: Map<String, Node>): Long {
    var counter = 0L
    val currentNodes: Array<String> = nodes.values.filter { it.id.endsWith("A") }.map { it.id }.toTypedArray()
    val distToZ: Array<Int> = currentNodes.map { getOrCalculate(nodes, it, counter, directions).first }.toTypedArray()
    val nextZ: Array<String> = currentNodes.map { getOrCalculate(nodes, it, counter, directions).second }.toTypedArray()
    while (true) {
        val min = distToZ.min()
        counter += min
        var all0 = true
        for (i in distToZ.indices) {
            distToZ[i] = distToZ[i] - min
            if (distToZ[i] == 0) {
                val (dist, node) = getOrCalculate(nodes, nextZ[i], counter, directions)
                distToZ[i] = dist
                nextZ[i] = node
            } else {
                all0 = false
            }
        }//193364026951
        if (all0) return counter
    }
}

private fun parse(input: List<String>): Pair<String, Map<String, Node>> {
    val first = input[0]
    val nodes = input.drop(1).dropWhile { it.isBlank() }.associate {
        val (id, directions) = it.split(" = ")
        val (left, right) = directions.substring(1, directions.length - 1).split(", ")
        id to Node(id, left, right, first.length)
    }
    return first to nodes.toMap()
}

private fun part1(start: String, directions: String, nodes: Map<String, Node>): Int {
    var counter = 0
    var currentNode = start
    while (!currentNode.endsWith("Z")) {
//    while (currentNode != "ZZZ") {
        val d = directions[counter % directions.length]
        currentNode = when(d) {
            'R' -> nodes[currentNode]!!.right
            'L' -> nodes[currentNode]!!.left
            else -> throw RuntimeException()
        }
        counter++

    }
    return counter
}

private fun part2(directions: String, nodes: Map<String, Node>): Long {
    var counter = 0L
    val currentNodes: Array<String> = nodes.values.filter { it.id.endsWith("A") }.map { it.id }.toTypedArray()
    while (!currentNodes.all { it.endsWith("Z") }) {
        val d = directions[(counter % directions.length).toInt()]
        for (i in currentNodes.indices) {
            currentNodes[i] = when(d) {
                'R' -> nodes[currentNodes[i]]!!.right
                'L' -> nodes[currentNodes[i]]!!.left
                else -> throw RuntimeException()
            }

        }
        counter++
    }
    return counter
}


fun main() {
    val input = readInput("Day08")
    val (directions, node) = parse(input)
    // 11795205644011
    node.values.filter { it.id.endsWith("A") }.map { it.id }.fold(1) { seed, it -> seed * part1(it, directions, node) }.println()
//    test(directions, node).println()
}