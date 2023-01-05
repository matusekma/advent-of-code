enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun getRightDirection(): Direction {
        return when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            RIGHT -> DOWN
            LEFT -> UP
        }
    }

    fun getLeftDirection(): Direction {
        return when (this) {
            RIGHT -> UP
            LEFT -> DOWN
            DOWN -> RIGHT
            UP -> LEFT
        }
    }
}

class State22(val currentDir: Direction, val currentPos: Position, val grid: MutableList<MutableList<Char>>)

class Square(val minPos: Position, val maxPos: Position)

class Day22 {
    private val NOTHING = ' '
    private val EMPTY = '.'
    private val WALL = '#'

    fun part1(input: List<String>): Int {
        val grid = mutableListOf<MutableList<Char>>()
        val commandsString = input.last()
        val steps = commandsString.split('L', 'R')
        val turns = commandsString.split(Regex("[0-9]+")).filter { it.isNotEmpty() }
        val commands = mutableListOf<String>()
        for (i in steps.indices) {
            commands.add(steps[i])
            if (i < turns.size)
                commands.add(turns[i])
        }
        println(commands)

        val longestRow = input.subList(0, input.size - 2).maxOf { it.length }
        for (line in input.subList(0, input.size - 2)) {
            val row = CharArray(longestRow) { ' ' }
            for (i in line.indices) {
                row[i] = line[i]
            }
            grid.add(row.toMutableList())
        }

        val currentPos = Position(input[0].indexOfFirst { it == EMPTY }, 0)
        var state = State22(Direction.RIGHT, currentPos, grid)
        for (command in commands) {
//            for (y in 0 until grid.size) {
//                for (x in 0 until grid[0].size) {
//                    if (x == state.currentPos.x && y == state.currentPos.y) {
//                        print("A")
//                    } else {
//                        print(grid[y][x])
//                    }
//                }
//                println()
//            }
//            println()
            val step = command.toIntOrNull()
            if (step != null) {
                repeat(step) {
                    state = stepInCurrentDirection(state)
                }
            } else {
                state = changeDirection(command, state)
            }
        }

        return 1000 * (state.currentPos.y + 1) + 4 * (state.currentPos.x + 1) + getFacingScore(state.currentDir)
    }

    private fun getFacingScore(currentDir: Direction): Int {
        return when (currentDir) {
            Direction.UP -> 3
            Direction.DOWN -> 1
            Direction.RIGHT -> 0
            Direction.LEFT -> 2
        }
    }

    private fun changeDirection(dir: String, state: State22): State22 {
        if (dir == "L") {
            return State22(state.currentDir.getLeftDirection(), state.currentPos, state.grid)
        } else if (dir == "R") {
            return State22(state.currentDir.getRightDirection(), state.currentPos, state.grid)
        }
        throw RuntimeException("Invalid turn direction")
    }

    private fun stepInCurrentDirection(state: State22): State22 {
        val grid = state.grid
        val currentPos = state.currentPos
        when (state.currentDir) {
            Direction.UP -> {
                return if (currentPos.y - 1 < 0 || grid[currentPos.y - 1][currentPos.x] == NOTHING) {
                    val newPos = findMaxYInColumnOrSameIfWall(currentPos, grid)
                    State22(state.currentDir, newPos, state.grid)
                } else if (grid[currentPos.y - 1][currentPos.x] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x, currentPos.y - 1), state.grid)
                }
            }

            Direction.DOWN -> {
                return if (currentPos.y + 1 == grid.size || grid[currentPos.y + 1][currentPos.x] == NOTHING) {
                    val newPos = findMinYInColumnOrSameIfWall(currentPos, grid)
                    State22(state.currentDir, newPos, state.grid)
                } else if (grid[currentPos.y + 1][currentPos.x] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x, currentPos.y + 1), state.grid)
                }
            }

            Direction.LEFT -> {
                return if (currentPos.x - 1 < 0 || grid[currentPos.y][currentPos.x - 1] == NOTHING) {
                    val newPos = findMaxXInRowOrSameIfWall(currentPos, grid[currentPos.y])
                    State22(state.currentDir, newPos, state.grid)
                } else if (grid[currentPos.y][currentPos.x - 1] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x - 1, currentPos.y), state.grid)
                }
            }

            Direction.RIGHT -> {
                return if (currentPos.x + 1 == grid[currentPos.y].size || grid[currentPos.y][currentPos.x + 1] == NOTHING) {
                    val newPos = findMinXInRowOrSameIfWall(currentPos, grid[currentPos.y])
                    State22(state.currentDir, newPos, state.grid)
                } else if (grid[currentPos.y][currentPos.x + 1] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x + 1, currentPos.y), state.grid)
                }
            }
        }
    }

    private fun findMaxXInRowOrSameIfWall(currentPos: Position, row: MutableList<Char>): Position {
        for (x in row.size - 1 downTo 0) {
            if (row[x] == EMPTY) {
                return Position(x, currentPos.y)
            }
            if (row[x] == WALL) {
                return currentPos
            }
        }
        return currentPos
    }

    private fun findMinXInRowOrSameIfWall(currentPos: Position, row: MutableList<Char>): Position {
        for (x in 0 until row.size) {
            if (row[x] == EMPTY) {
                return Position(x, currentPos.y)
            }
            if (row[x] == WALL) {
                return currentPos
            }
        }
        return currentPos
    }

    private fun findMaxYInColumnOrSameIfWall(currentPos: Position, grid: MutableList<MutableList<Char>>): Position {
        for (y in grid.size - 1 downTo 0) {
            if (grid[y][currentPos.x] == EMPTY) {
                return Position(currentPos.x, y)
            }
            if (grid[y][currentPos.x] == WALL) {
                return currentPos
            }
        }
        return currentPos
    }

    private fun findMinYInColumnOrSameIfWall(currentPos: Position, grid: MutableList<MutableList<Char>>): Position {
        for (y in 0 until grid.size) {
            if (grid[y][currentPos.x] == EMPTY) {
                return Position(currentPos.x, y)
            }
            if (grid[y][currentPos.x] == WALL) {
                return currentPos
            }
        }
        return currentPos
    }

    private val A = Square(Position(50, 0), Position(99, 49))
    private val B = Square(Position(100, 0), Position(149, 49))
    private val C = Square(Position(50, 50), Position(99, 99))
    private val D = Square(Position(0, 100), Position(49, 149))
    private val E = Square(Position(50, 100), Position(99, 149))
    private val F = Square(Position(0, 150), Position(49, 199))

    fun part2(input: List<String>): Int {
        val grid = mutableListOf<MutableList<Char>>()
        val commandsString = input.last()
        val steps = commandsString.split('L', 'R')
        val turns = commandsString.split(Regex("[0-9]+")).filter { it.isNotEmpty() }
        val commands = mutableListOf<String>()
        for (i in steps.indices) {
            commands.add(steps[i])
            if (i < turns.size)
                commands.add(turns[i])
        }
        println(commands)

        val longestRow = input.subList(0, input.size - 2).maxOf { it.length }
        for (line in input.subList(0, input.size - 2)) {
            val row = CharArray(longestRow) { ' ' }
            for (i in line.indices) {
                row[i] = line[i]
            }
            grid.add(row.toMutableList())
        }

        val currentPos = Position(input[0].indexOfFirst { it == EMPTY }, 0)
        var state = State22(Direction.RIGHT, currentPos, grid)
        for (y in 0 until grid.size) {
            print(y)
            for (x in 0 until grid[0].size) {
                if (x == state.currentPos.x && y == state.currentPos.y) {
                    print("A")
                } else {
                    print(grid[y][x])
                }
            }
            println()
        }
        println()
        println(commands)
        for (command in commands) {
            val step = command.toIntOrNull()
            if (step != null) {
                repeat(step) {
                    state = stepInCurrentDirection2(state)
//                    for (y in 0 until grid.size) {
//                        print(y)
//                        for (x in 0 until grid[0].size) {
//                            if (x == state.currentPos.x && y == state.currentPos.y) {
//                                print("A")
//                            } else {
//                                print(grid[y][x])
//                            }
//                        }
//                        println()
//                    }
//                    println()
                }
            } else {
                state = changeDirection(command, state)
            }
        }

        return 1000 * (state.currentPos.y + 1) + 4 * (state.currentPos.x + 1) + getFacingScore(state.currentDir)

    }

    private fun stepInCurrentDirection2(state: State22): State22 {
        val grid = state.grid
        val currentPos = state.currentPos
        when (state.currentDir) {
            Direction.UP -> {
                return if (currentPos.y - 1 < 0 || grid[currentPos.y - 1][currentPos.x] == NOTHING) {
                    val newPos: Position
                    val newDirection: Direction
                    if (currentPos.y == B.minPos.y && currentPos.x >= B.minPos.x) { // B -> F ok
                        newPos = Position(currentPos.x % 50, F.maxPos.y)
                        newDirection = Direction.UP
                    } else if (currentPos.y == A.minPos.y && currentPos.x >= A.minPos.x) { // A -> F ok
                        newPos = Position(F.minPos.x, F.minPos.y + currentPos.x % 50)
                        newDirection = Direction.RIGHT
                    } else {        // D -> C  ok
                        newPos = Position(C.minPos.x, C.minPos.y + currentPos.x % 50)
                        newDirection = Direction.RIGHT
                    }
                    if (grid[newPos.y][newPos.x] == WALL) {
                        state
                    } else {
                        State22(newDirection, newPos, state.grid)
                    }
                } else if (grid[currentPos.y - 1][currentPos.x] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x, currentPos.y - 1), state.grid)
                }
            }

            Direction.DOWN -> {
                return if (currentPos.y + 1 == grid.size || grid[currentPos.y + 1][currentPos.x] == NOTHING) {
                    val newPos: Position
                    val newDirection: Direction
                    if (currentPos.y == B.maxPos.y && currentPos.x >= B.minPos.x) { // B -> C ok
                        newPos = Position(C.maxPos.x, C.minPos.y + currentPos.x % 50)
                        newDirection = Direction.LEFT
                    } else if (currentPos.y == E.maxPos.y && currentPos.x >= E.minPos.x) { // E -> F ok
                        newPos = Position(F.maxPos.x, F.minPos.y + currentPos.x % 50)
                        newDirection = Direction.LEFT
                    } else {        // F -> B ok
                        newPos = Position(B.minPos.x + currentPos.x % 50, B.minPos.y)
                        newDirection = Direction.DOWN
                    }
                    if (grid[newPos.y][newPos.x] == WALL) {
                        state
                    } else {
                        State22(newDirection, newPos, state.grid)
                    }
                } else if (grid[currentPos.y + 1][currentPos.x] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x, currentPos.y + 1), state.grid)
                }
            }

            Direction.LEFT -> {
                return if (currentPos.x - 1 < 0 || grid[currentPos.y][currentPos.x - 1] == NOTHING) {
                    val newPos: Position
                    val newDirection: Direction
                    if (currentPos.x == A.minPos.x && currentPos.y <= A.maxPos.y) { // A -> D ok
                        newPos = Position(D.minPos.x, D.maxPos.y - currentPos.y % 50)
                        newDirection = Direction.RIGHT
                    } else if (currentPos.x == C.minPos.x && currentPos.y <= C.maxPos.y) { // C -> D ok
                        newPos = Position(currentPos.y % 50, D.minPos.y)
                        newDirection = Direction.DOWN
                    } else if (currentPos.x == D.minPos.x && currentPos.y <= D.maxPos.y) { // D -> A ok
                        newPos = Position(A.minPos.x, A.maxPos.y - currentPos.y % 50)
                        newDirection = Direction.RIGHT
                    } else {        // F -> A ok
                        newPos = Position(A.minPos.x + currentPos.y % 50, A.minPos.y)
                        newDirection = Direction.DOWN
                    }
                    if (grid[newPos.y][newPos.x] == WALL) {
                        state
                    } else {
                        State22(newDirection, newPos, state.grid)
                    }
                } else if (grid[currentPos.y][currentPos.x - 1] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x - 1, currentPos.y), state.grid)
                }
            }

            Direction.RIGHT -> {
                return if (currentPos.x + 1 == grid[currentPos.y].size || grid[currentPos.y][currentPos.x + 1] == NOTHING) {
                    val newPos: Position
                    val newDirection: Direction
                    if (currentPos.x == B.maxPos.x && currentPos.y <= B.maxPos.y) { // B -> E ok
                        newPos = Position(E.maxPos.x, E.maxPos.y - currentPos.y % 50)
                        newDirection = Direction.LEFT
                    } else if (currentPos.x == C.maxPos.x && currentPos.y <= C.maxPos.y) { // C -> B ok
                        newPos = Position(B.minPos.x + currentPos.y % 50, B.maxPos.y)
                        newDirection = Direction.UP
                    } else if (currentPos.x == E.maxPos.x && currentPos.y <= E.maxPos.y) { // E -> B ok
                        newPos = Position(B.maxPos.x, B.maxPos.y - currentPos.y % 50)
                        newDirection = Direction.LEFT
                    } else {        // F -> E
                        newPos = Position(E.minPos.x + currentPos.y % 50, E.maxPos.y)
                        newDirection = Direction.UP
                    }
                    if (grid[newPos.y][newPos.x] == WALL) {
                        state
                    } else {
                        State22(newDirection, newPos, state.grid)
                    }
                } else if (grid[currentPos.y][currentPos.x + 1] == WALL) {
                    state
                } else {
                    State22(state.currentDir, Position(currentPos.x + 1, currentPos.y), state.grid)
                }
            }
        }
    }
}

fun main() {
    val input = readInput("input22_1")
    //println(Day22().part1(input))
    println(Day22().part2(input))
}