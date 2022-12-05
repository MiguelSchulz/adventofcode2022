package puzzle

import Puzzle

object Day5 : Puzzle {
    override val day: Int = 5
    override val fileInputPath: String = "src/main/resources/stack_crates.txt"

    override fun solvePart1(input: String): String {
        val stacks = input.readStacks()
        val allMoves = input.filterInput()
        allMoves.forEach {
            stacks.moveOneByOne(it.first, it.second, it.third)
        }
        return stacks.firstItemsOnStacks()
    }

    override fun solvePart2(input: String): String {
        val stacks = input.readStacks()
        val allMoves = input.filterInput()
        allMoves.forEach {
            stacks.moveAtOnce(it.first, it.second, it.third)
        }
        return stacks.firstItemsOnStacks()
    }

    private fun String.readStacks(): MutableList<ArrayDeque<Char>> {
        val listOfStacks: MutableList<ArrayDeque<Char>> = mutableListOf()
        lines()
            .filter(String::isNotEmpty)
            .filter { !it.startsWith("move") }
            .reversed()
            .drop(1)
            .forEach {
                it.chunked(4)
                    .forEachIndexed { index, item ->
                        val letter = item[1]
                        if (letter.isUpperCase() && letter != ' ') {
                            if (!listOfStacks.indices.contains(index)) {
                                listOfStacks.add(index, ArrayDeque(listOf(letter)))
                            } else {
                                listOfStacks[index].addLast(letter)
                            }
                        }
                    }
            }
        return listOfStacks
    }

    private fun String.filterInput(): List<Triple<Int, Int, Int>> {
        return lines()
            .filter { it.startsWith("move") }
            .map {
                val cleanInput = it.replace("move ", "").split(" from ", " to ")
                Triple(cleanInput[0].toInt(), cleanInput[1].toInt(), cleanInput[2].toInt())
            }
    }
}

private fun List<ArrayDeque<Char>>.moveOneByOne(count: Int, from: Int, to: Int) {
    if (from - 1 < size && to - 1 < size) {
        val fromStack = get(from - 1)
        val toStack = get(to - 1)
        for (i in 0 until count) {
            if (fromStack.isNotEmpty()) toStack.addLast(fromStack.removeLast())
        }
    }
}

private fun MutableList<ArrayDeque<Char>>.moveAtOnce(count: Int, from: Int, to: Int) {

    if (from - 1 < size && to - 1 < size) {
        val fromStack = get(from - 1)
        val toStack = get(to - 1)

        val toMove = fromStack.takeLast(count)
        toStack.addAll(toMove)
        set(from - 1, ArrayDeque(fromStack.dropLast(count)))
    }
}

private fun List<ArrayDeque<Char>>.firstItemsOnStacks(): String {
    return mapNotNull { it.lastOrNull() }.joinToString("")
}