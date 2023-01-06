package aoc2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput

internal class Day02Test {

    private val day02 = Day02()
    private val testInput = readInput("aoc2020/Day02_test")

    @Nested
    inner class Part1 {

        @Test
        fun `check example input`() {
            assertEquals(2, day02.part1(testInput))
        }

    }

    @Nested
    inner class Part2 {

        @Test
        fun `check example input`() {
            assertEquals(1, day02.part2(testInput))
        }

    }
}