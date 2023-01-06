package aoc2020

import readInput

class Day02 {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val (times, c, pass) = line.split(' ')
            val (minTimes, maxTimes) = times.split('-').map { it.toInt() }
            val char = c[0]
            var count = 0
            for (p in pass) {
                if (p == char) {
                    count++
                    if (count > maxTimes) {
                        break
                    }
                }
            }
            if (count in minTimes..maxTimes) sum++
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val (indices, c, pass) = line.split(' ')
            val (pos1, pos2) = indices.split('-').map { it.toInt() - 1 }
            val firstPosContains = pass[pos1] == c[0]
            val secondPosContains = pass[pos2] == c[0]
            if (firstPosContains xor secondPosContains) sum++
        }
        return sum
    }
}


fun main() {
    val input = readInput("aoc2020/input02_1")
    println(Day02().part1(input))
    println(Day02().part2(input))
}
