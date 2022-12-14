import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day17Test {

    private val testInput = readInput("Day17_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(3068, Day17().part1(testInput, 2022))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(1514285714288, Day17().part2(testInput))
        }

    }
}