import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day8

class Day8Test {

    @Test
    fun trees_visible_are_correct() {
        val input = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

        Day8.solvePart1(input) shouldBe 21
    }

    @Test
    fun highest_scenic_score() {
        val input = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

        Day8.solvePart2(input) shouldBe 8
    }
}