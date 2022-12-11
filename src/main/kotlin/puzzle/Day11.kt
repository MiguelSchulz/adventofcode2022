package puzzle

import Puzzle

object Day11 : Puzzle {
    override val day: Int = 11
    override val fileInputPath: String = "src/main/resources/annoying_monkeys.txt"

    override fun solvePart1(input: String): Long {
        val monkeys = input.readMonkeys()

        playMonkeyGames(monkeys, 20, 3)
        val mostActiveMonkeys = monkeys.sortedByDescending { it.actionCount }.map { it.actionCount }

        return mostActiveMonkeys[0] * mostActiveMonkeys[1]
    }

    override fun solvePart2(input: String): Long {
        val monkeys = input.readMonkeys()

        playMonkeyGames(monkeys, 10000)
        val mostActiveMonkeys = monkeys.sortedByDescending { it.actionCount }.map { it.actionCount }
        println(mostActiveMonkeys)
        return mostActiveMonkeys[0] * mostActiveMonkeys[1]
    }

    private fun playMonkeyGames(monkeys: List<Monkey>, rounds: Int, worryLevelDecreaser: Int? = null) {
        val canDivideBy = monkeys.map { it.divisibleByTest }.reduce { a, b -> a * b }

        repeat(rounds) {// Monkeys play 20 rounds
            for (monkey in monkeys) {
                monkey.items.mutate {
                    monkey.actionCount++
                    // Perform the monkeys operation on all items the monkey holds
                    if (worryLevelDecreaser != null) {
                        // If there is a worryLevelDecreaser, apply that know
                        monkey.operation.perform(it) / worryLevelDecreaser
                    } else {
                        // Otherwise, use Modulo to keep numbers low since we do not care about the actual number
                        monkey.operation.perform(it) % canDivideBy
                    }
                }
                val toTrueMonkey = monkey.items.filter { it % monkey.divisibleByTest == 0L }
                val toFalseMonkey = monkey.items - toTrueMonkey
                monkeys[monkey.trueMonkeyThrow].items.addAll(toTrueMonkey)
                monkeys[monkey.falseMonkeyThrow].items.addAll(toFalseMonkey)
                monkey.items = mutableListOf()
            }
        }
    }

    class Monkey(
        var items: MutableList<Long> = mutableListOf(),
        val operation: Action,
        val divisibleByTest: Int,
        val trueMonkeyThrow: Int,
        val falseMonkeyThrow: Int,
        var actionCount: Long = 0,
    ) {
        companion object {
            fun readMonkey(input: List<String>): Monkey {
                return Monkey(
                    input[1].removePrefix("Starting items: ").split(", ").map { it.toLong() }.toMutableList(),
                    Action.fromString(input[2])!!,
                    input[3].removePrefix("Test: divisible by ").toInt(),
                    input[4].removePrefix("If true: throw to monkey ").toInt(),
                    input[5].removePrefix("If false: throw to monkey ").toInt(),
                )
            }
        }
    }

    sealed class Action {

        abstract fun perform(old: Long): Long

        object multiplyByItself : Action() {
            override fun perform(old: Long) = old * old
        }

        class addTo(val number: Long) : Action() {
            override fun perform(old: Long) = old + number
        }

        class multiplyBy(val number: Long) : Action() {
            override fun perform(old: Long) = old * number
        }

        companion object {
            fun fromString(string: String): Action? {
                val cleanString = string.removePrefix("Operation: new = old ")
                return when {
                    cleanString == "* old" -> multiplyByItself
                    cleanString.startsWith("+") -> {
                        addTo(cleanString.split(" ")[1].toLong())
                    }

                    cleanString.startsWith("*") && !cleanString.endsWith("old") -> {
                        multiplyBy(cleanString.split(" ")[1].toLong())
                    }

                    else -> {

                        null
                    }
                }
            }
        }
    }
}

private fun String.readMonkeys(): List<Day11.Monkey> {
    return lines()
        .filter(String::isNotEmpty)
        .map(String::trim)
        .chunked(6)
        .map(Day11.Monkey::readMonkey)
}

inline fun <T> MutableList<T>.mutate(transform: (T) -> T): MutableList<T> {
    return mutateIndexed { _, t -> transform(t) }
}

inline fun <T> MutableList<T>.mutateIndexed(transform: (Int, T) -> T): MutableList<T> {
    val iterator = listIterator()
    var i = 0
    while (iterator.hasNext()) {
        iterator.set(transform(i++, iterator.next()))
    }
    return this
}