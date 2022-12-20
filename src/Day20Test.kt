import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day20Test {

    private val testInput = readInput("Day20_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(3, Day20().part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(1623178306, Day20().part2(testInput))
        }

    }
}