import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day6

class Day6Test {

    @Test
    fun stream_should_have_4_matching() {
        val input = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"

        Day6.solvePart1(input) shouldBe 7
    }

    @Test
    fun stream_should_have_14_matching() {
        val input = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"

        Day6.solvePart2(input) shouldBe 19
    }
}