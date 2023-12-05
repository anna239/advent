import kotlin.math.min
//
//class RangeNode(val parent: RangeNode?, val lvl: Int, val sourceFrom: Long, val sourceTo: Long, val children: ArrayList<RangeNode>?) {
//    fun getRoot(): RangeNode {
//        return parent?.getRoot() ?: this
//    }
//}
//
//fun buildTree(seedRanges: List<Pair<Long, Long>>, ranges: List<List<Range>>): List<RangeNode> {
//    seedRanges.forEach { (from, to) ->
//        var currentLevel = 0
//        ranges[currentLevel].sortedBy { it.sourceFrom }.forEach { range }
//    }
//}


class Range(val sourceFrom: Long, val targetFrom: Long, val length: Long) {
    fun convert(source: Long): Long? {
        if (source >= sourceFrom && source < sourceFrom + length) {
            return targetFrom + (source - sourceFrom)
        }
        return null
    }
}

private fun getSeeds(input: String): List<Long> {
    return input.split(":")[1].trim().split(" ").map { it.toLong() }
}

private fun part1(lines: List<String>): Long {
    val seeds = getSeeds(lines[0])
    val ranges = parseRanges(lines)

    var min = Long.MAX_VALUE
    seeds.forEach { seed ->
        var current = seed
        ranges.forEach { r ->
            current = r.asSequence().mapNotNull { it.convert(current) }.firstOrNull() ?: current
        }
        min = min(min, current)

    }
    return min
}

fun getSeedRanges(input: String): List<Pair<Long, Long>> {
    val values = input.split(":")[1].trim().split(" ").map { it.toLong() }
    val it = values.iterator()
    val result = arrayListOf<Pair<Long, Long>>()
    while (it.hasNext()) {
        val start = it.next()
        result.add(start to start + it.next())
    }
    return result
}

private fun part2(lines: List<String>): Long {
    val seeds = getSeedRanges(lines[0])
    val ranges = parseRanges(lines)

    var min = Long.MAX_VALUE
    seeds.forEach { seedRange ->
        println("ananlyze range from ${seedRange.first} to ${seedRange.second}")
        (seedRange.first..<seedRange.second).forEach { seed ->
            var current = seed
            ranges.forEach { r ->
                current = r.asSequence().mapNotNull { it.convert(current) }.firstOrNull() ?: current
            }
            min = min(min, current)
        }
    }
    return min
}

private fun part2Fast(lines: List<String>): Long {
    val seeds = getSeedRanges(lines[0])
    val ranges = parseRanges(lines)

    var min = Long.MAX_VALUE
    seeds.forEach { seedRange ->
        println("ananlyze range from ${seedRange.first} to ${seedRange.second}")
        (seedRange.first..<seedRange.second).forEach { seed ->
            var current = seed
            ranges.forEach { r ->
                current = r.asSequence().mapNotNull { it.convert(current) }.firstOrNull() ?: current
            }
            min = min(min, current)
        }
    }
    return min
}

private fun parseRanges(lines: List<String>): ArrayList<ArrayList<Range>> {
    val ranges = ArrayList<ArrayList<Range>>()
    var currentRanges = ArrayList<Range>()
    ranges.add(currentRanges)
    for (line in lines.drop(3)) {
        if (line.isBlank()) continue
        if (line.contains("map:")) {
            currentRanges.sortBy { it.targetFrom }
            currentRanges = ArrayList()
            ranges.add(currentRanges)
            continue
        }
        val (targetFrom, sourceFrom, length) = line.split(" ")
        currentRanges.add(Range(sourceFrom.toLong(), targetFrom.toLong(), length.toLong()))
    }
    currentRanges.sortBy { it.targetFrom }
    return ranges
}

fun main() {
    val input = readInput("Day05")
    part2(input).println()

}