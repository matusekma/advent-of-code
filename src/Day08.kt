class Day08 {
    fun part1(grid: List<String>): Int {
        val width = grid[0].length
        val height = grid.size
        var visibleTrees = width * 2 + height * 2 - 4
        for (i in 1..height - 2) {
            for (j in 1..width - 2) {
                if (visibleInRow(grid, i, j) || visibleInColumn(grid, i, j)) {
                    visibleTrees++
                }
            }
        }
        return visibleTrees
    }

    private fun visibleInColumn(grid: List<String>, row: Int, column: Int): Boolean {
        var visibleDown = true
        for (i in row - 1 downTo 0) {
            if (grid[i][column] >= grid[row][column]) {
                visibleDown = false
                break;
            }
        }
        var visibleUp = true
        for (i in row + 1 until grid.size) {
            if (grid[i][column] >= grid[row][column]) {
                visibleUp = false
                break;
            }
        }
        return visibleUp || visibleDown
    }

    private fun visibleInRow(grid: List<String>, row: Int, column: Int): Boolean {
        var visibleLeft = true
        for (j in column - 1 downTo 0) {
            if (grid[row][j] >= grid[row][column]) {
                visibleLeft = false
                break;
            }
        }
        var visibleRight = true
        for (j in column + 1 until grid[0].length) {
            if (grid[row][j] >= grid[row][column]) {
                visibleRight = false
                break;
            }
        }
        return visibleLeft || visibleRight
    }

    fun part2(grid: List<String>): Int {
        var highestScore = 0
        val width = grid[0].length
        val height = grid.size
        for (i in 1..height - 2) {
            for (j in 1..width - 2) {
                val score = scoreUp(grid, i, j) * scoreDown(grid, i, j) * scoreLeft(grid, i, j) * scoreRight(grid, i, j)
                if (score > highestScore) highestScore = score
            }
        }
        return highestScore
    }

    private fun scoreUp(grid: List<String>, row: Int, column: Int): Int {
        var scoreUp = 0
        for (i in row + 1 until grid.size) {
            scoreUp++
            if (grid[i][column] >= grid[row][column]) {
                return scoreUp
            }
        }
        return scoreUp
    }

    private fun scoreDown(grid: List<String>, row: Int, column: Int): Int {
        var scoreUp = 0
        for (i in row - 1 downTo 0) {
            scoreUp++
            if (grid[i][column] >= grid[row][column]) {
                return scoreUp
            }
        }
        return scoreUp
    }

    private fun scoreLeft(grid: List<String>, row: Int, column: Int): Int {
        var scoreUp = 0
        for (j in column - 1 downTo 0) {
            scoreUp++
            if (grid[row][j] >= grid[row][column]) {
                return scoreUp
            }
        }
        return scoreUp
    }

    private fun scoreRight(grid: List<String>, row: Int, column: Int): Int {
        var scoreUp = 0
        for (j in column + 1 until grid[0].length) {
            scoreUp++
            if (grid[row][j] >= grid[row][column]) {
                return scoreUp
            }
        }
        return scoreUp
    }
}

fun main() {
    val day08 = Day08()
    val input = readInput("input08_1")
    println(day08.part1(input))
    println(day08.part2(input))
}