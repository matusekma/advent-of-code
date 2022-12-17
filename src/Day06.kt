class Day06 {
    fun part1(input: String): Int {
        for (i in 0..input.length - 4) {
            if (input.substring(i, i + 4).toCharArray().toSet().size == 4) {
                return i + 4
            }
        }
        return -1
    }

    fun part2(input: String): Int {
        for (i in 0..input.length - 14) {
            if (input.substring(i, i + 14).toCharArray().toSet().size == 14) {
                return i + 14
            }
        }
        return -1
    }
}

fun main() {
    val day06 = Day06()
    val input = readInput("input06_1")
    println(day06.part1(input[0]))
    println(day06.part2(input[0]))
}