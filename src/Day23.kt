import java.util.Collections

enum class Direction23 {
    NORTH, SOUTH, WEST, EAST
}

data class ElfPosition(val x: Int, val y: Int) {

    fun getFieldsInDirection(direction: Direction23): List<ElfPosition> {
        return when (direction) {
            Direction23.NORTH -> getNorthFields()
            Direction23.SOUTH -> getSouthFields()
            Direction23.WEST -> getWestFields()
            Direction23.EAST -> getEastFields()
        }
    }

    fun getFieldInMainDirection(direction: Direction23): ElfPosition {
        return when (direction) {
            Direction23.NORTH -> northPos()
            Direction23.SOUTH -> southPos()
            Direction23.WEST -> westPos()
            Direction23.EAST -> eastPos()
        }
    }

    private fun getNorthFields() = listOf(northPos(), northEastPos(), northWestPos())
    private fun getSouthFields() = listOf(southPos(), southEastPos(), southWestPos())
    private fun getWestFields() = listOf(westPos(), northWestPos(), southWestPos())
    private fun getEastFields() = listOf(eastPos(), northEastPos(), southEastPos())
    fun getAdjFields() = listOf(
        northPos(),
        southPos(),
        eastPos(),
        westPos(),
        northEastPos(),
        northWestPos(),
        southEastPos(),
        southWestPos()
    )

    private fun northPos() = ElfPosition(this.x, this.y - 1)
    private fun southPos() = ElfPosition(this.x, this.y + 1)
    private fun eastPos() = ElfPosition(this.x + 1, this.y)
    private fun westPos() = ElfPosition(this.x - 1, this.y)
    private fun northEastPos() = ElfPosition(this.x + 1, this.y - 1)
    private fun southEastPos() = ElfPosition(this.x + 1, this.y + 1)
    private fun northWestPos() = ElfPosition(this.x - 1, this.y - 1)
    private fun southWestPos() = ElfPosition(this.x - 1, this.y + 1)

}

class Elf(var position: ElfPosition)

class Day23 {
    private val occupiedFields = mutableMapOf<ElfPosition, Elf>()
    private val currentConsideredDirections =
        mutableListOf(Direction23.NORTH, Direction23.SOUTH, Direction23.WEST, Direction23.EAST)
    private val currentConsideredMoves = mutableMapOf<ElfPosition, Elf>()
    private val alreadyConsideredMovesInRound = mutableSetOf<ElfPosition>()

    fun part1(input: List<String>): Int {
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == '#') {
                    val position = ElfPosition(x, y)
                    val elf = Elf(position)
                    occupiedFields[position] = elf
                }
            }
        }

        var noMove = false
        var rounds = 0
        while (!noMove) {  // change this to repeat(10) for round 1
            rounds++
            considerMoves()
            val movesPerformed = performMoves()
            if (movesPerformed == 0) {
                noMove = true
            }
            Collections.rotate(currentConsideredDirections, -1)
        }

        val elfes = occupiedFields.values
        val maxX = elfes.maxOf { it.position.x }
        val minX = elfes.minOf { it.position.x }
        val maxY = elfes.maxOf { it.position.y }
        val minY = elfes.minOf { it.position.y }
        val rectangleSize =
            (maxX - minX + 1) * (maxY - minY + 1)
        println(rounds)  // round2
        return rectangleSize - elfes.size
    }

    private fun performMoves(): Int {
        var movesPerformed = 0
        for ((position, elf) in currentConsideredMoves.entries) {
            occupiedFields.remove(elf.position)
            occupiedFields[position] = Elf(position)
            movesPerformed++
        }
        return movesPerformed
    }

    private fun considerMoves() {
        currentConsideredMoves.clear()
        alreadyConsideredMovesInRound.clear()
        for (elf in occupiedFields.values) {
            if (elf.position.getAdjFields().all { !occupiedFields.containsKey(it) }) {
                continue // no move
            } else {
                for (direction in currentConsideredDirections) {
                    if (elf.position.getFieldsInDirection(direction).all { !occupiedFields.containsKey(it) }) {
                        val consideredPosition = elf.position.getFieldInMainDirection(direction)
                        if (!alreadyConsideredMovesInRound.contains(consideredPosition)) {
                            currentConsideredMoves[consideredPosition] = elf
                            alreadyConsideredMovesInRound.add(consideredPosition)
                        } else {
                            currentConsideredMoves.remove(consideredPosition)
                        }
                        break // do not continue considering
                    }
                }
            }
        }
    }

}

fun main() {
    val input = readInput("input23_1")
    println(Day23().part1(input))
}