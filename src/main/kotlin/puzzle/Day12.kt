package puzzle

import Puzzle
import java.awt.Point

object Day12 : Puzzle {
    override val day: Int = 12
    override val fileInputPath: String = "src/main/resources/shortest_path.txt"

    override fun solvePart1(input: String): Int {
        val (grid, start, end) = input.lines().filter(String::isNotEmpty).createHeightMap()
        return shortestPathLength(grid, start, end)
    }

    override fun solvePart2(input: String): Int {
        val (grid, _, end) = input.lines().filter(String::isNotEmpty).createHeightMap()

        return grid.filter { it.value == 0 }
            .keys
            .minOf { shortestPathLength(grid, it, end) }
    }
}

private fun shortestPathLength(grid: Map<Point, Int>, start: Point, end: Point): Int {
    val steps = mutableMapOf(start to 0)
    val queue = ArrayDeque(listOf(start))

    while (queue.isNotEmpty()) {
        val point = queue.removeLast()
        val currentElevation = grid[point]!!
        val currentSteps = steps[point]!!

        val neighbors = point.neighbors()
            .filter {
                if (it in grid) grid[it]!! <= currentElevation + 1 else false
            }
            .mapNotNull {
                val oldSteps = steps[it]
                steps[it] = (currentSteps + 1).coerceAtMost(oldSteps ?: Int.MAX_VALUE)
                if (steps[it] != oldSteps) it else null
            }
        queue += neighbors
    }
    return steps[end] ?: Int.MAX_VALUE
}

private fun Point.neighbors() = setOf(
    Point(x - 1, y),
    Point(x + 1, y),
    Point(x, y + 1),
    Point(x, y - 1),
)

private fun List<String>.createHeightMap(): ElfMap {
    var start: Point? = null
    var end: Point? = null

    val grid = flatMapIndexed { x, line ->
        line.mapIndexed { y, char ->
            val point = Point(x, y)
            Point(x, y) to when (char) {
                'S' -> {
                    start = point
                    0
                }

                'E' -> {
                    end = point
                    'z' - 'a'
                }

                else -> char - 'a'
            }
        }
    }.toMap()

    return ElfMap(
        grid,
        requireNotNull(start),
        requireNotNull(end)
    )
}

private data class ElfMap(
    val grid: Map<Point, Int>,
    val start: Point,
    val end: Point
)