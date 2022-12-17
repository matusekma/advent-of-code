import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day05Test {

    private val day05 = Day05()
    private val testInput = readInput("Day05_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(2, day05.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(4, day05.part2(testInput))
        }

    }
}