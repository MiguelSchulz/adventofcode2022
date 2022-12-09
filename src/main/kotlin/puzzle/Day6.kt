package puzzle

import Puzzle


object Day6 : Puzzle {
    override val day: Int = 6
    override val fileInputPath: String = "src/main/resources/communication_stream.txt"

    override fun solvePart1(input: String): Int {
        return input.firstIndexWithCharacters()
    }

    override fun solvePart2(input: String): Int {
        return input.firstIndexWithCharacters(count = 14)
    }
}

private fun String.firstIndexWithCharacters(count: Int = 4): Int {
    var pointer = 0
    while (
        indices.contains(pointer + count) &&
        !substring(pointer until pointer + count).allCharsDifferent()
    ) {
        pointer += 1
    }
    return pointer + count
}

private fun String.allCharsDifferent() = all(hashSetOf<Char>()::add)