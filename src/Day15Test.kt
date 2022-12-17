import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day15Test {

    private val day15 = Day15()
    private val testInput = readInput("Day15_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(26, day15.part1(testInput, 10))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(56000011, day15.part2(testInput))
        }

    }
}