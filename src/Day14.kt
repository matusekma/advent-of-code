import java.lang.Integer.max
import java.lang.Integer.min

open class Position(val x: Int, val y: Int) {
    val posKey: String
        get() = "${this.x},${this.y}"
}

class Day14 {
    private val occupiedCoordinates = mutableMapOf<String, Position>()

    fun part1(input: List<String>): Int {
        for (line in input) {
            createRocks(line)
        }
        val maxY = occupiedCoordinates.values.maxOf { it.y }
        var currentY = 0
        var sandCounter = 0
        while (currentY <= maxY) {
            var sand = Position(500, 0)
            var sandStopped = false
            while (!sandStopped && currentY <= maxY) {
                val positionDown = Position(sand.x, sand.y + 1)
                if (!occupiedCoordinates.containsKey(positionDown.posKey)) {
                    sand = positionDown
                } else {
                    val positionDownLeft = Position(sand.x - 1, sand.y + 1)
                    if (!occupiedCoordinates.containsKey(positionDownLeft.posKey)) {
                        sand = positionDownLeft
                    } else {
                        val positionDownRight = Position(sand.x + 1, sand.y + 1)
                        if (!occupiedCoordinates.containsKey(positionDownRight.posKey)) {
                            sand = positionDownRight
                        } else {
                            occupiedCoordinates[sand.posKey] = sand
                            sandCounter++
                            sandStopped = true
                        }
                    }
                }
                currentY = sand.y
            }
        }
        return sandCounter
    }

    private fun createRocks(line: String) {
        val positions = line.split(" -> ").map { posString ->
            val coordStrings = posString.split(',')
            Position(coordStrings[0].toInt(), coordStrings[1].toInt())
        }

        for (i in 0 until positions.size - 1) {
            val startPosition = positions[i]
            val endPosition = positions[i + 1]
            if (endPosition.x == startPosition.x) { // move vertically
                for (y in min(startPosition.y, endPosition.y)..max(startPosition.y, endPosition.y)) {
                    val rockPosition = Position(startPosition.x, y)
                    occupiedCoordinates[rockPosition.posKey] = rockPosition
                }
            } else { // move horizontally
                for (x in min(startPosition.x, endPosition.x)..max(startPosition.x, endPosition.x)) {
                    val rockPosition = Position(x, startPosition.y)
                    occupiedCoordinates[rockPosition.posKey] = rockPosition
                }
            }
        }

    }

    fun part2(input: List<String>): Int {
        for (line in input) {
            createRocks(line)
        }
        val maxY = occupiedCoordinates.values.maxOf { it.y }
        val floorY = maxY + 2
        var currentSandPosition = Position(-1, -1)
        var sandCounter = 0
        while (currentSandPosition.posKey != Position(500, 0).posKey) {
            var sand = Position(500, 0)
            var sandStopped = false
            while (!sandStopped) {
                val positionDown = Position(sand.x, sand.y + 1)
                if (!occupiedCoordinates.containsKey(positionDown.posKey) && positionDown.y != floorY) {
                    sand = positionDown
                } else {
                    val positionDownLeft = Position(sand.x - 1, sand.y + 1)
                    if (!occupiedCoordinates.containsKey(positionDownLeft.posKey) && positionDown.y != floorY) {
                        sand = positionDownLeft
                    } else {
                        val positionDownRight = Position(sand.x + 1, sand.y + 1)
                        if (!occupiedCoordinates.containsKey(positionDownRight.posKey) && positionDown.y != floorY) {
                            sand = positionDownRight
                        } else {
                            occupiedCoordinates[sand.posKey] = sand
                            sandCounter++
                            sandStopped = true
                        }
                    }
                }
                currentSandPosition = sand
            }
        }
        return sandCounter
    }
}

fun main() {
    val input = readInput("input14_1")
    println(Day14().part1(input))
    println(Day14().part2(input))
}