import java.util.*

class Cube(val x: Int, val y: Int, val z: Int) {
    val coordKey: String
        get() = "${this.x},${this.y},${this.z}"

    fun add(vector: Cube): Cube {
        return Cube(this.x + vector.x, this.y + vector.y, this.z + vector.z)
    }
}

class Day18 {
    fun part1(input: List<String>): Int {
        val cubeCoords = mutableSetOf<String>()
        val cubes = mutableListOf<Cube>()

        var allSides = input.size * 6
        for (line in input) {
            cubeCoords.add(line)
            val (x, y, z) = line.split(',').map { it.toInt() }
            cubes.add(Cube(x, y, z))
        }

        for (c in cubes) {
            val neighborCooords = listOf(
                Cube(c.x, c.y, c.z + 1),
                Cube(c.x, c.y, c.z - 1),
                Cube(c.x, c.y + 1, c.z),
                Cube(c.x, c.y - 1, c.z),
                Cube(c.x + 1, c.y, c.z),
                Cube(c.x - 1, c.y, c.z)
            )
            for (n in neighborCooords) {
                if (cubeCoords.contains(n.coordKey)) {
                    allSides--
                }
            }
        }

        return allSides
    }


    fun part2(input: List<String>): Int {
        val WORLD_SIZE = 25
        val coords = Array(WORLD_SIZE) { Array(WORLD_SIZE) { IntArray(WORLD_SIZE) { 0 } } }

        for (line in input) {
            val (x, y, z) = line.split(',').map { it.toInt() }
            coords[x + 1][y + 1][z + 1] = 1
        }

        val foundSides = mutableSetOf<String>()
        println(iterativeDFS(coords, Cube(0, 0, 0), mutableSetOf(), foundSides, WORLD_SIZE))
        //println(visited.size)
        println(foundSides.size)

        return foundSides.size
    }
}

//fun DFS(graph: Array<Array<IntArray>>, c: Cube, visited: MutableSet<String>, foundSides: MutableSet<String>, WORLD_SIZE: Int) {
//    visited.add(c.coordKey)
//    val neighbors = listOf(
//        Cube(c.x, c.y, c.z + 1),
//        Cube(c.x, c.y, c.z - 1),
//        Cube(c.x, c.y + 1, c.z),
//        Cube(c.x, c.y - 1, c.z),
//        Cube(c.x + 1, c.y, c.z),
//        Cube(c.x - 1, c.y, c.z)
//    ).filter { it.x >= 0 && it.y >= 0 && it.z >= 0 && it.x < WORLD_SIZE && it.y < WORLD_SIZE && it.z < WORLD_SIZE}
//
//    val cubeNeighbors = neighbors.filter { graph[it.x][it.y][it.z] == 1 }
//    cubeNeighbors.forEach { foundSides.add(it.coordKey + "-" + c.coordKey) }
//    val freeNeighbors = neighbors.filter { graph[it.x][it.y][it.z] == 0 }
//    for (n in freeNeighbors) {
//        if (!visited.contains(n.coordKey)) {
//            DFS(graph, n, visited, foundSides, WORLD_SIZE)
//        }
//    }
//}

fun main() {
    val input = readInput("input18_1")
    //println(Day18().part1(input))
    println(Day18().part2(input))
}

fun iterativeDFS(graph: Array<Array<IntArray>>, c: Cube, visited: MutableSet<String>, foundSides: MutableSet<String>, WORLD_SIZE: Int): MutableSet<String> {
    val stack: Stack<Cube> = Stack()
    stack.push(c)
    while (!stack.empty()) {
        // Pop a vertex from the stack
        val current = stack.pop()

        // if the vertex is already discovered yet, ignore it
        if (visited.contains(current.coordKey)) {
            continue
        }

        // we will reach here if the popped vertex `v` is not discovered yet;
        // print `v` and process its undiscovered adjacent nodes into the stack
        visited.add(current.coordKey)

        val neighbors = listOf(
            Cube(current.x, current.y, current.z + 1),
            Cube(current.x, current.y, current.z - 1),
            Cube(current.x, current.y + 1, current.z),
            Cube(current.x, current.y - 1, current.z),
            Cube(current.x + 1, current.y, current.z),
            Cube(current.x - 1, current.y, current.z)
        ).filter { it.x >= 0 && it.y >= 0 && it.z >= 0 && it.x < WORLD_SIZE && it.y < WORLD_SIZE && it.z < WORLD_SIZE}

        val cubeNeighbors = neighbors.filter { graph[it.x][it.y][it.z] == 1 }
        cubeNeighbors.forEach { foundSides.add(it.coordKey + "-" + current.coordKey) }
        val freeNeighbors = neighbors.filter { graph[it.x][it.y][it.z] == 0 }
        for (n in freeNeighbors) {
            if (!visited.contains(n.coordKey)) {
                stack.push(n)
            }
        }
    }
    return foundSides
}