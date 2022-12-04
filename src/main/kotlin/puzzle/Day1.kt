package puzzle

import Puzzle

@Suppress("unused")
object Day1 : Puzzle {
    override val fileInputPath: String = "src/main/resources/elv_calories.txt"
    override val day: Int = 2

    override fun solvePart1(input: String): Int {
        val elves = elvesFromInputString(input)
        return mostPackedElves(elves, 1).firstOrNull() ?: 0
    }


    override fun solvePart2(input: String): Int {
        val elves = elvesFromInputString(input)
        return mostPackedElves(elves).sum()
    }
}

private data class Elf(
    val foodItems: List<Int>
)
private fun elvesFromInputString(string: String): List<Elf> {
    return string.split("\n\n")
        .map {
            Elf(
                it.split("\n")
                .map(String::toIntOrNull)
                .filterNotNull()
            )
        }
}

private fun mostPackedElves(elves: List<Elf>, count: Int = 3): List<Int> {
    return elves.map { it.foodItems.sum() }
        .sortedDescending()
        .take(count)
}