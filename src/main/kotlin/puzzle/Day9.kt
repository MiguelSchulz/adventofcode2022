package puzzle

import Puzzle
import java.awt.Point

object Day9 : Puzzle {
    override val day: Int = 9
    override val fileInputPath: String = "src/main/resources/rope_bridge.txt"

    override fun solvePart1(input: String): Int {
        val currentHead = Point(0, 0)
        val currentTail = Point(0, 0)
        val fieldsAlreadyVisited = mutableSetOf<Point>()

        fieldsAlreadyVisited.add(currentTail.copy())

        input.lines()
            .filter(String::isNotEmpty)
            .map(String::mapToMovePair)
            .forEach { movePair ->
                repeat(movePair.second) {
                    currentHead.moveIn(movePair.first)
                    currentTail.follow(currentHead)
                    fieldsAlreadyVisited.add(currentTail.copy())
                }
            }
        return fieldsAlreadyVisited.count()
    }

    override fun solvePart2(input: String): Int {
        val followingPoints = buildList {
            repeat(10) { add(Point(0, 0))}
        }
        val fieldsAlreadyVisited = mutableSetOf<Point>()

        fieldsAlreadyVisited.add(followingPoints.last().copy())

        input.lines()
            .filter(String::isNotEmpty)
            .map(String::mapToMovePair)
            .forEach { movePair ->
                repeat(movePair.second) {
                    followingPoints.first().moveIn(movePair.first)
                    followingPoints.drop(1).forEachIndexed { index, point ->
                        point.follow(followingPoints[index])
                    }
                    fieldsAlreadyVisited.add(followingPoints.last().copy())
                }
            }
        return fieldsAlreadyVisited.count()
    }
}

private fun Point.copy(): Point = Point(x, y)

private fun Point.moveIn(direction: String) {
    when (direction) {
        "R" -> move(x + 1, y)
        "L" -> move(x - 1, y)
        "U" -> move(x, y - 1)
        "D" -> move(x, y + 1)
    }
}

private fun Point.follow(head: Point) {
    if (head in this.neighbors() || head == this) return

    val offsetX = if (head.x < x) -1 else 1
    val offsetY = if (head.y < y) -1 else 1

    when {
        head.x == x -> move(x, y + offsetY)
        head.y == y -> move(x + offsetX, y)
        else -> move(x + offsetX, y + offsetY)
    }
}

private fun Point.neighbors(): Set<Point> {
    return buildSet {
        (-1 until 2).forEach { i ->
            (-1 until 2).forEach { j ->
                add(Point(x + i, y + j))
            }
        }
    }
}

private fun String.mapToMovePair(): Pair<String, Int> {
    return split(" ").let { Pair(it[0], it[1].toInt()) }
}