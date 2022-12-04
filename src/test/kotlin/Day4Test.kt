import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day4Test {

     val input = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent()
    @Test
    fun two_ranges_are_fully_contained() {
        Day4.solvePart1(input) shouldBe 2
    }

    @Test
    fun ranges_have_overlapping_item() {
        Day4.solvePart2(input) shouldBe 4
    }
}