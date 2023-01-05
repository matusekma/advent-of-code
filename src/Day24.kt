import kotlin.properties.Delegates

data class Position24(var x: Int, var y: Int) {
    private fun upPos() = Position24(this.x, this.y - 1)
    private fun downPos() = Position24(this.x, this.y + 1)
    private fun rightPos() = Position24(this.x + 1, this.y)
    private fun leftPos() = Position24(this.x - 1, this.y)

    fun distance(pos: Position24): Int {
        val xDiff = (this.x - pos.x)
        val yDiff = (this.y - pos.y)
        return xDiff * xDiff + yDiff * yDiff
    }

    fun getAdjAndSelfDirections() = setOf(
        upPos(),
        downPos(),
        leftPos(),
        rightPos(),
        this
    )
}

data class Blizzard(val position: Position24, val direction: Direction)

class State24(val position: Position24, val currentSteps: Int)

class Day24 {
    val minX = 1
    val minY = 1
    var maxX by Delegates.notNull<Int>()
    var maxY by Delegates.notNull<Int>()
    var minSteps = 1000
    private lateinit var endPosition: Position24
    private val startPosition = Position24(1, 0)
    private val possibleBlizzards = setOf('>', '<', '^', 'v')
    val blizzards = mutableSetOf<Blizzard>()

    fun part1(input: List<String>): Int {
        endPosition = Position24(input[0].length - 2, input.size - 1)
        maxX = input[0].length - 2
        maxY = input.size - 2

        for (y in input.indices) {
            for (x in input[y].indices) {
                val c = input[y][x]
                if (possibleBlizzards.contains(c)) {
                    val position = Position24(x, y)
                    val blizzard = Blizzard(position, parseDirection(c))
                    blizzards.add(blizzard)
                }
            }
        }

        val initialState = State24(startPosition, 0)
        var queue: Set<State24> = setOf(initialState)
        var time = 0
        while (queue.isNotEmpty()) {
            time++
            val newBlizzardPositions = blizzards.map { getNewBlizzardPosition(it, time) }.toSet()
            val possibleNextPositions =
                queue.flatMap { it.position.getAdjAndSelfDirections() }
                    .filter {
                        ((it.y >= minY && it.x >= minX && it.x <= maxX && it.y <= maxY) || it == startPosition || it == endPosition)
                                && !newBlizzardPositions.contains(it)
                    }.toSet()
            if (possibleNextPositions.contains(endPosition)) {
                queue = emptySet()
            } else {
                queue = possibleNextPositions.map { State24(it, time) }.toSet()
            }
        }
        return time
    }

    private fun parseDirection(d: Char): Direction =
        when (d) {
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            else -> throw RuntimeException("Invalid direction")
        }


    private fun getNewBlizzardPosition(blizzard: Blizzard, steps: Int): Position24 {
        val position = blizzard.position
        return when (blizzard.direction) {
            Direction.UP -> Position24(position.x, (position.y - steps).mod(maxY))
            Direction.DOWN -> Position24(position.x, (position.y + steps).mod(maxY))
            Direction.LEFT -> Position24((position.x - steps).mod(maxX), position.y)
            Direction.RIGHT -> Position24((position.x + steps).mod(maxX), position.y)
        }
    }

    private fun Int.mod(b: Int): Int {
        var temp = this % b
        if (temp <= 0) {
            temp += b
        }
        return temp
    }

//    private fun getNextBlizzardState(
//        currentSteps: Int,
//        blizzards: MutableMap<Position24, List<Blizzard>>
//    ): MutableMap<Position24, List<Blizzard>> {
//        if (blizzardStatesByMinute.containsKey(currentSteps)) {
//            return blizzardStatesByMinute[currentSteps]!!
//        }
//        val newBlizzardState = mutableMapOf<Position24, List<Blizzard>>()
//        for ((position, bs) in blizzards.entries) {
//            for (blizzard in bs) {
//                val newPosition = getNewBlizzardPosition(position, blizzard.direction)
//                if (newBlizzardState.contains(newPosition)) {
//                    newBlizzardState[newPosition] = newBlizzardState[newPosition]!! + blizzard
//                } else {
//                    newBlizzardState[newPosition] = mutableListOf(blizzard)
//                }
//            }
//        }
//        blizzardStatesByMinute[currentSteps] = newBlizzardState
//        return newBlizzardState
//    }

    private fun searchBFS(state: State24) {
        val newBlizzardPositions = blizzards.map { getNewBlizzardPosition(it, state.currentSteps) }
        val possibleNextPositions =
            state.position.getAdjAndSelfDirections()
                .filter {
                    ((it.y >= minY && it.x >= minX && it.x <= maxX && it.y <= maxY) || it == startPosition || it == endPosition)
                            && !newBlizzardPositions.contains(it)
                }
        if (possibleNextPositions.contains(endPosition)) {
            if (state.currentSteps < minSteps) {
                minSteps = state.currentSteps
            }
            return
        }
        for (possibleNextPosition in possibleNextPositions.sortedBy { it.distance(endPosition) }) {     // consider positions bringing closer first
            val newState = State24(possibleNextPosition, state.currentSteps + 1)
            searchBFS(newState)
        }
    }

    fun part2(input: List<String>): Int {
        endPosition = Position24(input[0].length - 2, input.size - 1)
        maxX = input[0].length - 2
        maxY = input.size - 2

        for (y in input.indices) {
            for (x in input[y].indices) {
                val c = input[y][x]
                if (possibleBlizzards.contains(c)) {
                    val position = Position24(x, y)
                    val blizzard = Blizzard(position, parseDirection(c))
                    blizzards.add(blizzard)
                }
            }
        }

        val initialState = State24(startPosition, 0)
        var queue: Set<State24> = setOf(initialState)
        var time = 0
        val destinations = listOf(endPosition, startPosition, endPosition)
        for (destination in destinations) {
            while (queue.isNotEmpty()) {
                time++
                val newBlizzardPositions = blizzards.map { getNewBlizzardPosition(it, time) }.toSet()
                val possibleNextPositions =
                    queue.flatMap { it.position.getAdjAndSelfDirections() }
                        .filter {
                            ((it.y >= minY && it.x >= minX && it.x <= maxX && it.y <= maxY) || it == startPosition || it == endPosition)
                                    && !newBlizzardPositions.contains(it)
                        }.toSet()
                if (possibleNextPositions.contains(destination)) {
                    break
                } else {
                    queue = possibleNextPositions.map { State24(it, time) }.toSet()
                }
            }
            queue = setOf(State24(destination, time))
        }

        return time
    }
}

fun main() {
    val input = readInput("input24_1")
    println(Day24().part1(input))
    println(Day24().part2(input))
}