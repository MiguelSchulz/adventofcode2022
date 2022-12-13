import puzzle.Day13
import java.io.File

typealias Today = Day13

fun main() {
    val input = File(Today.fileInputPath)
        .readText(Charsets.UTF_8)

    println(Today.solvePart1(input))
    println(Today.solvePart2(input))
}