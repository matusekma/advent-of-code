import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day22Test {

    private val day22 = Day22()
    private val testInput = readInput("Day22_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(6032, day22.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(70, day22.part2(testInput))
        }

    }
}