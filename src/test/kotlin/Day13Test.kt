import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day13

class Day13Test {

    val input = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    @Test
    fun sum_of_right_packet_indices_adds_up() {
        Day13.solvePart1(input) shouldBe 13
    }

    @Test
    fun divider_key_should_be_correct() {
        Day13.solvePart2(input) shouldBe 140
    }
}