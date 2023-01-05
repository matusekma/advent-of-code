import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day24Test {

    private val day24 = Day24()
    private val testInput = readInput("Day24_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(18, day24.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(54, day24.part2(testInput))
        }

    }
}