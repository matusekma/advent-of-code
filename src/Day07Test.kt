import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day07Test {
    private val day07 = Day07()
    private val testInput = readInput("Day07_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(95437, day07.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(4, day07.part2(testInput))
        }

    }
}