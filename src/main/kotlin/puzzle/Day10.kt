package puzzle

import Puzzle

object Day10 : Puzzle {
    override val day: Int = 9
    override val fileInputPath: String = "src/main/resources/cpu_operations.txt"

    override fun solvePart1(input: String): Int {
        var X = 1
        var currentCycleIndex = 1

        return input.lines()
            .map {
                val operation = Operation.fromString(it)
                if (operation is Operation.AddX) {
                    listOfNotNull(Operation.NoOp, operation)
                } else {
                    listOfNotNull(operation)
                }
            }
            .flatten()
            .sumOf {
                currentCycleIndex += 1
                if (it is Operation.AddX) {
                    X += it.value
                }
                if ((currentCycleIndex + 20) % 40 == 0) {
                    currentCycleIndex * X
                } else {
                    0
                }
            }
    }

    override fun solvePart2(input: String): String {
        var currentX = 1
        return input.lines()
            .map {
                val operation = Operation.fromString(it)
                if (operation is Operation.AddX) {
                    listOfNotNull(Operation.NoOp, operation)
                } else {
                    listOfNotNull(operation)
                }
            }
            .flatten()
            .chunked(40)
            .map { chunk ->
                chunk
                    .mapIndexed { index, operation ->
                        if (operation is Operation.AddX) {
                            currentX += operation.value
                        }
                        if (index + 1 in currentX.toSprite()) {
                            "#"
                        } else {
                            "."
                        }
                    }
            }
            .map { listOf("#") + it.dropLast(1) + listOf("\n") } // First is always drawn, offset index by 1
            .flatten()
            .toList()
            .joinToString("")
            .also { println(it) }
    }

    sealed class Operation {
        object NoOp: Operation()
        data class AddX(
            val value: Int
        ): Operation()

        companion object {
            fun fromString(input: String): Operation? {
                return when {
                    input.startsWith("noop") -> NoOp
                    input.startsWith("addx") -> AddX(input.split(" ")[1].toInt())
                    else -> null
                }
            }
        }
    }
}

private fun Int.toSprite(): IntRange = (this - 1) until (this + 2)