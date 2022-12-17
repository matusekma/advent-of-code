import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day03Test {

    private val day03 = Day03()
    private val testInput = readInput("Day03_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(157, day03.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(70, day03.part2(testInput))
        }

    }
}