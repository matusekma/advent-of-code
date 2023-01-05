import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day21Test {

    private val day21 = Day21()
    private val testInput = readInput("Day21_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(152L, day21.part1(testInput))
        }

    }
}