class Day01 {
    fun part1MostCalories(input: List<String>): Int {
        var max = 0
        var current = 0
        for (calorie in input) {
            if (calorie == "") {
                if (current > max) {
                    max = current
                }
                current = 0
            } else {
                current += calorie.toInt()
            }
        }
        return max
    }

    fun part2Top3MostCalories(input: List<String>): Int {
        val maxes = IntArray(3)
        var current = 0
        for (calorie in input) {
            if (calorie == "") {
                if (current > maxes[2]) {
                    maxes[0] = maxes[1]
                    maxes[1] = maxes[2]
                    maxes[2] = current
                } else if (current > maxes[1]) {
                    maxes[0] = maxes[1]
                    maxes[1] = current
                } else if (current > maxes[0]) {
                    maxes[0] = current
                }
                current = 0
            } else {
                current += calorie.toInt()
            }
        }
        return maxes.sum()
    }

}

fun main() {
    val day01 = Day01()
    val input = readInput("input01_1")
    println(day01.part1MostCalories(input))
    println(day01.part2Top3MostCalories(input))
}
