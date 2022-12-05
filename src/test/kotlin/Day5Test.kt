import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import puzzle.Day5

class Day5Test {

    @Test
    fun top_crates_moved_one_by_one() {
        val input = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()

        Day5.solvePart1(input) shouldBe "CMZ"
    }

    @Test
    fun top_crates_moved_at_once() {
        val input = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()

        Day5.solvePart2(input) shouldBe "MCD"
    }
}