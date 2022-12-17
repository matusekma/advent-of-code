import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day04Test {

    private val day04 = Day04()
    private val testInput = readInput("Day04_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(2, day04.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(4, day04.part2(testInput))
        }

    }
}