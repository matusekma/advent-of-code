import kotlin.math.max

enum class RockType {
    ROW, CROSS, COLUMN, L, SQUARE
}

data class FallResult(val rock: Rock, val isLanded: Boolean)

class Rock(val parts: List<Position>, private val type: RockType) {

    fun moveRight(chamber: MutableList<MutableList<Char>>): Rock {
        if (this.parts.all { chamber[0].size > it.x + 1 && chamber[it.y][it.x + 1] == '.' }) {
            return Rock(this.parts.map { Position(it.x + 1, it.y) }, this.type)
        }
        return this
    }

    fun moveLeft(chamber: MutableList<MutableList<Char>>): Rock {
        if (this.parts.all { it.x - 1 >= 0 && chamber[it.y][it.x - 1] == '.' }) {
            return Rock(this.parts.map { Position(it.x - 1, it.y) }, this.type)
        }
        return this
    }

    fun fall(chamber: MutableList<MutableList<Char>>): FallResult {
        if (this.parts.all { it.y + 1 < chamber.size && chamber[it.y + 1][it.x] == '.' }) {
            return FallResult(Rock(this.parts.map { Position(it.x, it.y + 1) }, this.type), false)
        }
        return FallResult(this, true)
    }
}

class Day17 {
    val debug = false
    val chamberWidth = 7
    val appearDist = 3

    private val chamber = MutableList<MutableList<Char>>(4) { MutableList(7) { '.' } }

    private fun placeRock(currentHeight: Int, currentNumOfRocks: Long): Rock {
        return when (currentNumOfRocks % 5) {
            0L -> Rock((2..5).map { Position(it, chamber.size - (currentHeight + 4)) }, RockType.ROW)
            1L -> Rock(
                listOf(
                    Position(3, chamber.size - (currentHeight + 4)),
                    Position(2, chamber.size - (currentHeight + 5)),
                    Position(3, chamber.size - (currentHeight + 5)),
                    Position(4, chamber.size - (currentHeight + 5)),
                    Position(3, chamber.size - (currentHeight + 6))
                ), RockType.CROSS
            )

            2L -> Rock(
                listOf(
                    Position(2, chamber.size - (currentHeight + 4)),
                    Position(3, chamber.size - (currentHeight + 4)),
                    Position(4, chamber.size - (currentHeight + 4)),
                    Position(4, chamber.size - (currentHeight + 5)),
                    Position(4, chamber.size - (currentHeight + 6))
                ), RockType.L
            )

            3L -> Rock((0..3).map { Position(2, chamber.size - (currentHeight + 4 + it)) }, RockType.COLUMN)
            4L -> Rock(
                listOf(
                    Position(2, chamber.size - (currentHeight + 4)),
                    Position(3, chamber.size - (currentHeight + 4)),
                    Position(2, chamber.size - (currentHeight + 5)),
                    Position(3, chamber.size - (currentHeight + 5)),
                ), RockType.SQUARE
            )

            else -> throw RuntimeException("Error: rock placement")
        }
    }

    fun part1(input: List<String>, numOfRocks: Int): Int {
        val directions = input[0]
        var currentDir = 0
        var currentNumOfRocks = 0L
        var currentHeight = 0
        while (currentNumOfRocks < numOfRocks) {
            while (currentHeight + 4 + 4 > chamber.size) {  // make chamber bigger
                chamber.add(0, MutableList(7) { '.' })
            }

            var rock = placeRock(currentHeight, currentNumOfRocks)
            // move
            var isLanded = false
            printChamber(rock)
            while (!isLanded) {
                rock = if (directions[currentDir] == '>') rock.moveRight(chamber) else rock.moveLeft(chamber)
                val fallResult = rock.fall(chamber)
                rock = fallResult.rock
                isLanded = fallResult.isLanded
                printChamber(rock)
                if (currentDir + 1 == directions.length) currentDir = 0 else currentDir++
            }

            // finish
            updateChamberWithRock(rock)
            currentHeight = max(currentHeight, chamber.size - rock.parts.minOf { it.y })
            currentNumOfRocks++
        }

        return currentHeight
    }

    private fun updateChamberWithRock(rock: Rock) {
        for (part in rock.parts) {
            chamber[part.y][part.x] = '#'
        }
    }

    private fun printChamber(rock: Rock) {
        if (!debug) return
        for (i in 0 until chamber.size) {
            for (j in 0 until chamber[i].size) {
                val part = rock.parts.find { it.y == i && it.x == j }
                if (part != null) print('@') else print(chamber[i][j])
            }
            println()
        }
        println()
    }


    fun part2(input: List<String>): Long {
        val states = mutableMapOf<String, List<Long>>()
        val directions = input[0]
        var currentDir = 0
        var currentNumOfRocks = 0L
        var currentHeight = 0L
        var additional = 0L
        while (currentNumOfRocks < 1000000000000) {
            while (currentHeight + 4 + 4 > chamber.size) {  // make chamber bigger
                chamber.add(0, MutableList(7) { '.' })
            }

            var rock = placeRock(currentHeight.toInt(), currentNumOfRocks)
            // move
            var isLanded = false
            while (!isLanded) {
                rock = if (directions[currentDir] == '>') rock.moveRight(chamber) else rock.moveLeft(chamber)
                val fallResult = rock.fall(chamber)
                rock = fallResult.rock
                isLanded = fallResult.isLanded
                printChamber(rock)
                if (currentDir + 1 == directions.length) currentDir = 0 else currentDir++
            }

            // finish
            updateChamberWithRock(rock)
            currentHeight = max(currentHeight.toInt(), chamber.size - rock.parts.minOf { it.y }).toLong()
            currentNumOfRocks++

            var columnHeights = mutableListOf<Int>()
            for (i in 0 until chamberWidth) {
                var found = false
                var j = 0
                while (!found && j != chamber.size) {
                    if (chamber[j][i] != '.') {
                        found = true
                    } else j++
                }
                columnHeights.add((currentHeight - (chamber.size - j)).toInt())
            }

            val currentState = listOf(
                currentDir,
                columnHeights.joinToString(";"),
                currentNumOfRocks % 5
            ).joinToString(";")

            if (states.containsKey(currentState) && additional == 0L) {  // additional not yet defined, COMPUTE REPEATING CYCLES HERE
                val (previousNumOfRocks, previousHeight) = states[currentState]!!
                val repeat = (1000000000000 - currentNumOfRocks) / (currentNumOfRocks - previousNumOfRocks)
                currentNumOfRocks += repeat * (currentNumOfRocks-previousNumOfRocks)
                additional = repeat * (currentHeight - previousHeight)

            }
            states[currentState] = listOf(currentNumOfRocks, currentHeight)
        }
        return currentHeight + additional
    }
}

fun main() {
    val input = readInput("input17_1")
    println(Day17().part1(input, 2022))
    println(Day17().part2(input))
}