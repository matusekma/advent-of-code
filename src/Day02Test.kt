import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val day02 = Day02()
    private val testInput = readInput("Day02_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(15, day02.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(12, day02.part2(testInput))
        }

    }
}