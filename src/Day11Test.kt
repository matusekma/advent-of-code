import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val day11 = Day11()

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(10605, day11.part1())
        }

    }
    // 13588389524

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(70, day11.part2())
        }

    }
}