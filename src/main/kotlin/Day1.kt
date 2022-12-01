import java.io.File

fun day1() {
    val input = File("src/main/resources/elv_calories.txt").readText(Charsets.UTF_8)

    val elves = elvesFromInputString(input)

    println("The Elf with the most calories carries ${mostCalorieOf(elves)} calories")
    println("The three Elves with the most calories carry ${mostPackedElves(elves).sum()} in total")
}

private data class Elf(
    val foodItems: MutableList<Int> = mutableListOf()
)
private fun elvesFromInputString(string: String): List<Elf> {
    val elves = mutableListOf<Elf>()
    var currentElf = Elf()
    for (line in string.lines()) {
        val lineString = line.toString()
        if (lineString.isEmpty()) {
            elves.add(currentElf.copy())
            currentElf = Elf()
        }
        else
        {
            lineString.toIntOrNull()?.let { currentElf.foodItems.add(it) }
        }
    }
    return elves
}
private fun mostCalorieOf(elves: List<Elf>): Int {
    return mostPackedElves(elves, 1).firstOrNull() ?: 0
}

private fun mostPackedElves(elves: List<Elf>, count: Int = 3): List<Int> {
    return elves.map { it.foodItems.sum() }
        .sortedDescending()
        .take(count)
}