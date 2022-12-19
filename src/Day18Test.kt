import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day18Test {

    private val day18 = Day18()
    private val testInput = readInput("Day18_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(64, day18.part1(testInput))
        }

        @Test
        fun `check example input 1`() {
            assertEquals(10, day18.part1(listOf("1,1,1", "2,1,1")))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(58, Day18().part2(testInput))
        }

        @Test
        fun `check example input 1`() {
            assertEquals(10, Day18().part2(listOf("1,1,1", "2,1,1")))
        }

    }
}