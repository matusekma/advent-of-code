import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val day10 = Day10()
    private val testInput = readInput("Day10_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(13140, day10.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(70, day10.part2(testInput))
        }

    }
}