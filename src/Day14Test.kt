import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day14Test {

    private val day14 = Day14()
    private val testInput = readInput("Day14_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(24, day14.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(93, day14.part2(testInput))
        }

    }
}