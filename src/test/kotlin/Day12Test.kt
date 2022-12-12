import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day12

class Day12Test {

    val input = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()

    @Test
    fun should_find_shortest_path() {
        Day12.solvePart1(input) shouldBe 31
    }

    @Test
    fun should_find_shortest_path_from_all_starting_points() {
        Day12.solvePart2(input) shouldBe 29
    }
}