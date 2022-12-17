import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day16Test {

    private val day16 = Day16()
    private val testInput = readInput("Day16_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(1651, day16.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(1707, day16.part2(testInput))
        }

    }
}