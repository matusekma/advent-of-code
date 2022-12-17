import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day08Test {

    private val day08 = Day08()
    private val testInput = readInput("Day08_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(21, day08.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(8, day08.part2(testInput))
        }

    }
}