import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day14

class Day14Test {

    val input = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()
    @Test
    fun sand_that_can_rest_should_be() {
        Day14.solvePart1(input) shouldBe 24
    }

    @Test
    fun sand_source_blocked_after_should_be() {
        Day14.solvePart2(input) shouldBe 93
    }
}