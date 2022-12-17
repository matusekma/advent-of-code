import kotlin.math.abs

data class Pos(var x: Int, var y: Int) {
    fun isFar(otherPos: Pos): Boolean {
        return abs(this.x - otherPos.x) > 1 || abs(this.y - otherPos.y) > 1
    }

}

fun stepInDirection(pos: Pos, direction: String) {
    when (direction) {
        "U" -> {
            pos.y++
        }

        "D" -> {
            pos.y--
        }

        "L" -> {
            pos.x--
        }

        "R" -> {
            pos.x++
        }
    }
}

// TODO use only vectors here
fun follow(followed: Pos, follower: Pos) {
    if (follower.isFar(followed)) {
        // same column
        if (followed.x == follower.x) {
            follower.y += (followed.y - follower.y) / 2
//                if(currentHeadPos.y > follower.y) follower.y++
//                else follower.y--
        }
        // same row
        else if (followed.y == follower.y) {
            follower.x += (followed.x - follower.x) / 2
//                if(currentHeadPos.x > follower.x) follower.x++
//                else follower.x--
        }
        // diagonal
        else {
            val vector = Pos(followed.x - follower.x, followed.y - follower.y)
            if (abs(vector.x) > 1) vector.x /= 2
            if (abs(vector.y) > 1) vector.y /= 2
            follower.x += vector.x
            follower.y += vector.y
        }
    }
}

class Day09Part1 {
    private val visitedPositions = mutableSetOf("0;0")
    private val currentHeadPos = Pos(0, 0)
    private val currentTailPos = Pos(0, 0)

    fun run(moves: List<String>): Int {
        for (move in moves) {
            val (direction, steps) = move.split(' ')
            moveInDirection(direction, steps.toInt())
        }
        return visitedPositions.size
    }

    private fun moveInDirection(direction: String, steps: Int) {
        for (i in 1..steps) {
            stepInDirection(currentHeadPos, direction)
            follow(currentHeadPos, currentTailPos)
            visitedPositions.add("${currentTailPos.x};${currentTailPos.y}")
        }
    }

}

class Day09Part2 {
    private val visitedPositions = mutableSetOf("0;0")
    private val knots = List(10) { Pos(0, 0) }

    fun run(moves: List<String>): Int {
        for (move in moves) {
            val (direction, steps) = move.split(' ')
            moveInDirection(direction, steps.toInt())
        }
        return visitedPositions.size
    }

    private fun moveInDirection(direction: String, steps: Int) {
        for (i in 1..steps) {
            stepInDirection(knots[0], direction)
            for(j in 0 .. knots.size - 2) {
                follow(knots[j], knots[j + 1])
            }
            visitedPositions.add("${knots.last().x};${knots.last().y}")
        }
    }

}

fun main() {
    val input = readInput("input09_1")
    println(Day09Part1().run(input))
    println(Day09Part2().run(input))
}