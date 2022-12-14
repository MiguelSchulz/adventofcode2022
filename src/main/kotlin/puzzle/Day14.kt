package puzzle

import Puzzle
import java.awt.Point

object Day14 : Puzzle {
    override val fileInputPath: String = "src/main/resources/falling_sand.txt"
    override val day: Int = 14

    override fun solvePart1(input: String): Int {
        val rocks = input.toRockMap()

        return rocks.sandJourney()
    }

    override fun solvePart2(input: String): Int {
        val rocks = input.toRockMap()

        val groundY = rocks.keys.maxBy { it.y }.y + 2

        (0 until 2000).forEach {
            rocks[Point(it, groundY)] = '#'
        }

        return rocks.sandJourney { rocks[it] == null }
    }

    private fun MutableMap<Point, Char>.sandJourney(whileCondition: (Point) -> (Boolean) = { true }): Int {
        var sand = Point(500, 0)
        var counter = 0

        try {
            while (whileCondition(sand)) {
                while (sand.fallIfPossible(this)) {
                    continue
                }
                this[Point(sand.x, sand.y)] = 'o'
                sand = Point(500, 0)
                counter++
            }
        } catch (error: SandFreefallingException) {
            println(error.message)
        }
        return counter
    }

    private fun Point.fallIfPossible(rockMap: MutableMap<Point, Char>): Boolean {
        if (rockMap[Point(x, y + 1)] == null) {
            if (y < 10000) {
                // Bottom is free, fall down one
                move(x, y + 1)
                return true
            } else {
                throw SandFreefallingException("All sands have fallen")
            }

        } else if (rockMap[Point(x - 1, y + 1)] == null) {
            // left bottom is free, fall
            move(x - 1, y + 1)
            return true
        } else if (rockMap[Point(x + 1, y + 1)] == null) {
            // right bottom is free, fall
            move(x + 1, y + 1)
            return true
        }
        return false
    }

    private fun Point.allPointsTo(point: Point): List<Point> {
        return when {
            this.x == point.x && this.y == point.y -> listOf(Point(x, y))
            this.x != point.x && this.y == point.y -> {
                val from = this.x.coerceAtMost(point.x)
                val to = this.x.coerceAtLeast(point.x)
                (from until to + 1).map { Point(it, y) }
            }

            this.y != point.y && this.x == point.x -> {
                val from = this.y.coerceAtMost(point.y)
                val to = this.y.coerceAtLeast(point.y)
                (from until to + 1).map { Point(x, it) }
            }

            this.x != point.x && this.y != point.y -> throw Error("No diagonal lines")
            else -> emptyList()
        }
    }

    private fun String.toRockMap(): MutableMap<Point, Char> {
        val rocks = emptyMap<Point, Char>()
            .toMutableMap()

        this.split("\n")
            .forEach { rockLine ->
                val cornerRocks = rockLine.split(" -> ")
                    .map { rock ->
                        val rockCoordinates = rock.split(',')
                        Point(rockCoordinates[0].toInt(), rockCoordinates[1].toInt())
                    }

                cornerRocks.forEachIndexed { index, rock ->
                    if (index > 0) {
                        val priorRock = cornerRocks[index - 1]
                        val connectingRocks = priorRock.allPointsTo(rock)

                        connectingRocks.forEach { point ->
                            rocks[point] = '#'
                        }
                    }
                }
            }
        return rocks
    }
}

private class SandFreefallingException(override val message: String?): Exception()