import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day2Test {
    @Test
    fun `rock_paper_scissors_points_add_up`() {
        val input = """
            A Y
            B X
            C Z
        """.trimIndent()

        totalPointsStrategy1(input) shouldBe 15
    }

    @Test
    fun `test_all_possible_combinations`() {
        evaluateMatch(Pair(Move.ROCK, Move.ROCK)) shouldBe 4
        evaluateMatch(Pair(Move.ROCK, Move.PAPER)) shouldBe 8
        evaluateMatch(Pair(Move.ROCK, Move.SCISSORS)) shouldBe 3

        evaluateMatch(Pair(Move.PAPER, Move.ROCK)) shouldBe 1
        evaluateMatch(Pair(Move.PAPER, Move.PAPER)) shouldBe 5
        evaluateMatch(Pair(Move.PAPER, Move.SCISSORS)) shouldBe 9

        evaluateMatch(Pair(Move.SCISSORS, Move.ROCK)) shouldBe 7
        evaluateMatch(Pair(Move.SCISSORS, Move.PAPER)) shouldBe 2
        evaluateMatch(Pair(Move.SCISSORS, Move.SCISSORS)) shouldBe 6
    }
}