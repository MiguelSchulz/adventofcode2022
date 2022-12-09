package puzzle

import Puzzle

object Day8 : Puzzle {
    override val day: Int = 8
    override val fileInputPath: String = "src/main/resources/visible_trees.txt"

    override fun solvePart1(input: String): Int {
        val trees = readTrees(input)
        return trees.mapIndexed { rowIndex, treeRow ->
            treeRow.mapIndexed { columnIndex, _ ->
                isTreeVisible(trees, rowIndex, columnIndex)
            }
        }
            .flatten()
            .count { it }
    }

    override fun solvePart2(input: String): Int {
        val trees = readTrees(input)
        return trees.mapIndexed { rowIndex, treeRow ->
            treeRow.mapIndexed { columnIndex, _ ->
                scenicScore(trees, rowIndex, columnIndex)
            }
        }
            .flatten()
            .max()
    }

    private fun readTrees(input: String): List<List<Int>> {
        return input.lines()
            .map {
                it.toCharArray()
                    .map(Char::digitToInt)
            }
    }

    fun scenicScore(heights: List<List<Int>>, row: Int, col: Int): Int {
        var leftScore = 0
        var rightScore = 0
        for (c in (0 until col).reversed()) {
            leftScore++
            if (heights[row][c] >= heights[row][col]) {
                break
            }
        }
        for (c in col + 1 until heights[0].size) {
            rightScore++
            if (heights[row][c] >= heights[row][col]) {
                break
            }

        }

        var topScore = 0
        var bottomScore = 0
        for (r in (0 until row).reversed()) {
            topScore++
            if (heights[r][col] >= heights[row][col]) {
                break
            }

        }
        for (r in row + 1 until heights.size) {
            bottomScore++
            if (heights[r][col] >= heights[row][col]) {
                break
            }

        }

        return leftScore * rightScore * topScore * bottomScore
    }

    fun isTreeVisible(heights: List<List<Int>>, row: Int, col: Int): Boolean {
        var leftBlocked = false
        var rightBlocked = false
        for (c in 0 until col) {
            if (heights[row][c] >= heights[row][col]) {
                leftBlocked = true
                break
            }
        }
        for (c in col + 1 until heights[0].size) {
            if (heights[row][c] >= heights[row][col]) {
                rightBlocked = true
                break
            }
        }

        var topBlocked = false
        var bottomBlocked = false
        for (r in 0 until row) {
            if (heights[r][col] >= heights[row][col]) {
                topBlocked = true
                break
            }
        }
        for (r in row + 1 until heights.size) {
            if (heights[r][col] >= heights[row][col]) {
                bottomBlocked = true
                break
            }
        }

        return !(leftBlocked && rightBlocked && topBlocked && bottomBlocked)
    }
}