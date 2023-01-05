import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day23Test {

    private val day23 = Day23()
    private val testInput = readInput("Day23_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(110, day23.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

    }
}