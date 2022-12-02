import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val day01 = Day01()
    private val testInput = readInput("Day01_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(24000, day01.part1MostCalories(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(45000, day01.part2Top3MostCalories(testInput))
        }

    }
}