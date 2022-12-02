interface Puzzle {

    val day: Int
    val fileInputPath: String

    fun solvePart1(input: String): Any

    fun solvePart2(input: String): Any
}