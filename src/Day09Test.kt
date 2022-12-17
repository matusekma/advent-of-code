import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val day09Part1 = Day09Part1()
    private val day09Part2 = Day09Part2()
    private val testInput1 = readInput("Day09_test")
    private val testInput2 = readInput("Day09_test2")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(13, day09Part1.run(testInput1))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(1, day09Part2.run(testInput1))
        }

        @Test
        fun `check example input 2`() {
            assertEquals(36, day09Part2.run(testInput2))
        }

    }
}