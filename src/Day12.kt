open class Vertex(
    val name: Char,
    val height: Char,
    val edges: MutableList<Vertex> = mutableListOf()
)

private fun buildGraph(input: List<String>): MutableList<MutableList<Vertex>> {
    val grid = mutableListOf<MutableList<Vertex>>()
    for (line in input) {
        grid.add(line.toCharArray().map { Vertex(it, if (it == 'S') 'a' else if (it == 'E') 'z' else it) }
            .toMutableList())
    }
    for (row in grid) {
        for (i in 0 until row.size - 1) {
            if (row[i].height + 1 >= row[i + 1].height) {
                row[i].edges.add(row[i + 1])
            }
            if (row[i].height <= row[i + 1].height + 1) {
                row[i + 1].edges.add(row[i])
            }

        }
    }
    for (i in 0 until grid[0].size) {
        for (j in 0 until grid.size - 1) {
            if (grid[j][i].height + 1 >= grid[j + 1][i].height) {
                grid[j][i].edges.add(grid[j + 1][i])
            }
            if (grid[j][i].height <= grid[j + 1][i].height + 1) {
                grid[j + 1][i].edges.add(grid[j][i])
            }
        }
    }
    return grid
}

class Day12 {
    fun part1(input: List<String>): Double {
        val grid = buildGraph(input)
        var start: Vertex? = null
        var end: Vertex? = null
        grid.forEach { it.forEach { v -> if (v.name == 'S') start = v else if (v.name == 'E') end = v } }
        val vertexList = grid.flatten()
        return dijkstra(start!!, end!!, vertexList)
    }

    private fun dijkstra(startNode: Vertex, endNode: Vertex, vertexList: List<Vertex>): Double {
        val distances = mutableMapOf<Vertex, Double>()
        distances[startNode] = 0.0
        val q = mutableSetOf<Vertex>()
        for (vertex in vertexList) {
            if (vertex != startNode) distances[vertex] = Double.MAX_VALUE
            q.add(vertex)
        }

        while (q.isNotEmpty()) {
            val v = q.minBy { distances[it]!! }
            q.remove(v)

            for (neighbor in v.edges) {
                if (q.contains(neighbor)) {
                    val alt = distances[v]!! + 1.0
                    if (alt < distances[neighbor]!!) {
                        distances[neighbor] = alt
                    }
                }
            }
            if(v == endNode){
                distances[endNode]!!
            }
        }
        return distances[endNode]!!
    }

    fun part2(input: List<String>): Double {
        val grid = buildGraph(input)

        val starts = mutableListOf<Vertex>()
        var end: Vertex? = null
        grid.forEach { it.forEach { v -> if (v.height == 'a') starts.add(v) else if (v.name == 'E') end = v } }
        val vertexList = grid.flatten()
        return starts.map { dijkstra(it, end!!, vertexList) }.min()
    }
}

fun main() {
    val input = readInput("input12_1")
    println(Day12().part1(input))
    println(Day12().part2(input))
}