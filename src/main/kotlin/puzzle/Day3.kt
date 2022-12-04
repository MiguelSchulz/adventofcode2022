package puzzle

import Puzzle

object Day3 : Puzzle {
    override val day: Int = 3
    override val fileInputPath: String = "src/main/resources/rucksack_contents.txt"

    override fun solvePart1(input: String): Int {
        return input.split("\n")
            .mapNotNull(String::matchingItemInCompartments)
            .map(Char::backpackValue)
            .sum()
    }

    override fun solvePart2(input: String): Int {
        return input.split("\n")
            .chunked(3)
            .mapNotNull(List<String>::onlyCommonItemInBackpacks)
            .map(Char::backpackValue)
            .sum()
    }
}

private fun String.matchingItemInCompartments(): Char? {
    val firstString = substring(0..+length / 2 - 1)
    val secondString = substring(length / 2..length - 1)

    return firstString.toSet().intersect(secondString.toSet()).firstOrNull()
}

private fun List<String>.onlyCommonItemInBackpacks(): Char? {
    var commonElements = this.firstOrNull()?.toSet()
    for (compareString in this.drop(0)) {
        commonElements = commonElements?.toSet()?.intersect(compareString.toSet())
    }
    return commonElements?.firstOrNull()?.toChar()
}

private fun Char.backpackValue(): Int {
    if (isUpperCase()) return code - 38
    return code - 96
}

