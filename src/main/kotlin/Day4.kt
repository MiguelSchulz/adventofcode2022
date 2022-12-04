object Day4 : Puzzle {
    override val day: Int = 4
    override val fileInputPath: String = "src/main/resources/camp_cleanup.txt"

    override fun solvePart1(input: String): Int {
        return input
            .toIntRangePairs()
            .map(Pair<IntRange, IntRange>::oneRangeFullyContainedByOther)
            .count { it }
    }

    override fun solvePart2(input: String): Int {
        return input
            .toIntRangePairs()
            .map(Pair<IntRange, IntRange>::rangesHaveOverlappingItem)
            .count { it }
    }

    private fun String.toIntRangePairs(): List<Pair<IntRange, IntRange>> {
        return split("\n")
            .filter(String::isNotEmpty)
            .mapNotNull {
                val splitRanges = it.split(",")
                if (splitRanges.count() == 2) {
                    Pair(splitRanges[0], splitRanges[1])
                } else null
            }
            .map {
                Pair(it.first.toIntRange(), it.second.toIntRange())
            }
            .filterPairs()
    }
}

private fun String.toIntRange(): IntRange? {
    val splitString = split("-")
    if (splitString.count() == 2) return splitString[0].toInt()..splitString[1].toInt()
    return null
}
private fun Pair<IntRange, IntRange>.oneRangeFullyContainedByOther(): Boolean {
    return (first.first in second && first.last in second) || (second.first in first && second.last in first)
}

private fun Pair<IntRange, IntRange>.rangesHaveOverlappingItem(): Boolean {
    return first.intersect(second).isNotEmpty() || second.intersect(first).isNotEmpty()
}