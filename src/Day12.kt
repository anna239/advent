private fun getArrangements(
    sprigs: String,
    strFrom: Int,
    groups: List<Int>,
    groupsFrom: Int,
    currentGroupFrom: Int,
    dynamic: HashMap<Triple<Int, Int, Int>, Long>
): Long {
    fun getOrPut(strIdx: Int, groupIdx: Int, currGroup: Int): Long {
        return dynamic.getOrPut(Triple(strIdx, groupIdx, currGroup)) {getArrangements(sprigs, strIdx, groups, groupIdx, currGroup, dynamic)}
    }


    var groupIdx = groupsFrom
    var currentGroup = currentGroupFrom
    for (i in strFrom..<sprigs.length) {
        if (sprigs[i] == '.') {
            if (currentGroup > 0) return 0
            currentGroup = -1
        } else if (sprigs[i] == '#') {
            if (currentGroup == 0) {
                return 0
            } else if (currentGroup == -1) {
                if (groupIdx >= groups.size) return 0
                currentGroup = groups[groupIdx] - 1
                groupIdx++
            } else if (currentGroup > 0) {
                currentGroup--
            }
        } else {
            if (currentGroup > 0) {
                currentGroup--
            } else if (currentGroup == 0) {
                currentGroup = -1
            } else if (groupIdx != groups.size) {
                val nextGroup = groups[groupIdx]
                val good = getOrPut(i + 1, groupIdx, -1)
//                val good = dynamic.getOrPut(Triple(i + 1, groupIdx, -1)) { getArrangements(sprigs, i + 1, groups, groupIdx, -1, dynamic) }
//                val bad = dynamic.getOrPut(Triple(i + 1, groupIdx + 1, nextGroup - 1)) { getArrangements(sprigs, i + 1, groups, ++groupIdx, nextGroup - 1, dynamic) }
                val bad = getOrPut(i + 1, ++groupIdx, nextGroup - 1)
                return good + bad
            }
        }
    }
    return if (groupIdx == groups.size && currentGroup <= 0) 1 else 0
}

//
//private fun getArrangements(line: String): Long {
//    val (springs, groupInput) = line.split(' ')
//    val groups = groupInput.split(',').map { it.toInt() }
//    return getArrangements(springs, 0, groups, 0, -1)
//}

private fun getBiggerArrangements(line: String): Long {
    val (springs, groupInput) = line.split(' ')
    val newSprings = Array(5) { springs }.joinToString(separator = "?")
    val newGroups = Array(5) { groupInput }.joinToString(separator = ",")
    val groups = newGroups.split(',').map { it.toInt() }
    return getArrangements(newSprings, 0, groups, 0, -1, HashMap())
}
//
//private fun part1(lines: List<String>): Long {
//    return lines.fold(0) { seed, it ->
//        seed + getArrangements(it)
//    }
//}

private fun part2(lines: List<String>): Long {
    return lines.fold(0L) { seed, it ->
        seed + getBiggerArrangements(it).toLong()
    }
}

fun main() {
    val input = readInput("Day12")
    part2(input).println()
}