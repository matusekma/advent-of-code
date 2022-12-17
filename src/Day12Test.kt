import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day12Test {

    private val day12 = Day12()
    private val testInput = readInput("Day12_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(31.0, day12.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(29.0, day12.part2(testInput))
        }

    }
}