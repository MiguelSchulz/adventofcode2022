import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day9

class Day9Test {
    @Test
    fun tail_visited_places_at_least_once() {
        val input = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent()
        Day9.solvePart1(input) shouldBe 13
    }

    @Test
    fun long_tail_visited_places_at_least_once() {
        val input = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent()
        Day9.solvePart2(input) shouldBe 36
    }
}