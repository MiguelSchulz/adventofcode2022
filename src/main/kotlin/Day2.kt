object Day2 : Puzzle {

    override val fileInputPath: String = "src/main/resources/rock_paper_scissors.txt"
    override val day: Int = 2

    override fun solvePart1(input: String): Int {
        return input.split("\n")
            .filter { it.count() == 3 }
            .map { match ->
                val neededResult = GameResult.fromStrategy(match[2].toString())
                val enemyMove = Move.fromGame(match[0].toString())
                Pair(enemyMove, enemyMove?.correspondingMove(neededResult))
            }
            .filterPairs()
            .sumOf { evaluateMatch(it) }
    }


    override fun solvePart2(input: String): Int {
        return input.split("\n")
            .filter { it.count() == 3 }
            .map { match ->
                Pair(Move.fromGame(match[0].toString()), Move.fromGame(match[2].toString()))
            }
            .filterPairs()
            .sumOf { evaluateMatch(it) }
    }
}

fun <T, U> List<Pair<T?, U?>>.filterPairs(): List<Pair<T, U>> =
    mapNotNull { (t, u) ->
        if (t == null || u == null) null else t to u
    }

internal enum class GameResult(val points: Int) {
    WIN(6), DRAW(3), LOOSE(0);

    companion object {
        fun fromStrategy(strategyDescriptor: String): GameResult? {
            return when (strategyDescriptor) {
                "X" -> LOOSE
                "Y" -> DRAW
                "Z" -> WIN
                else -> null
            }
        }
    }
}

internal enum class Move(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun fromGame(moveDescriptor: String): Move? {
            return when (moveDescriptor) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> null
            }
        }
    }

    fun correspondingMove(result: GameResult?): Move? {
        return when (result) {
            GameResult.WIN -> when (this) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }

            GameResult.DRAW -> this
            GameResult.LOOSE -> when (this) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }

            else -> null
        }
    }

    fun winsAgainst(move: Move): GameResult {
        if (this == move) return GameResult.DRAW
        return when (this) {
            ROCK -> if (move == SCISSORS) GameResult.WIN else GameResult.LOOSE
            PAPER -> if (move == ROCK) GameResult.WIN else GameResult.LOOSE
            SCISSORS -> if (move == PAPER) GameResult.WIN else GameResult.LOOSE
        }
    }
}

internal fun evaluateMatch(game: Pair<Move, Move>): Int {
    return game.second.winsAgainst(game.first).points + game.second.points
}