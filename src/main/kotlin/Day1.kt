import java.io.File

fun day1() {
    val input = File("src/main/resources/elv_calories.txt").readText(Charsets.UTF_8)

    val elves = elvesFromInputString(input)

    println("The Elf with the most calories carries ${mostCalorieOf(elves)} calories")
    println("The three Elves with the most calories carry ${mostPackedElves(elves).sum()} in total")
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
private fun mostCalorieOf(elves: List<Elf>): Int {
    return mostPackedElves(elves, 1).firstOrNull() ?: 0
}

private fun mostPackedElves(elves: List<Elf>, count: Int = 3): List<Int> {
    return elves.map { it.foodItems.sum() }
        .sortedDescending()
        .take(count)
}