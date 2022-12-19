import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day19Test {

    private val testInput = readInput("Day19_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(33, Day19().part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(3472, Day19().part2(testInput))
        }

    }
}