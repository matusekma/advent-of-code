import java.util.StringJoiner
import kotlin.math.max

class RatedVertex(
    val name: String,
    val rate: Int,
    var edges: Set<RatedVertex> = setOf()
)

class Edge(val v1: RatedVertex, val v2: RatedVertex, val weight: Int)

class ParseResult(val vertex: RatedVertex, val neighbors: Set<String>)

class Day16 {
    var pressure = 0

    fun part1(input: List<String>): Int {
        val vertexes = mutableMapOf<String, RatedVertex>()
        val notZeroVertexes = mutableMapOf<String, RatedVertex>()
        val vertexNeighbors = mutableMapOf<String, Set<String>>()
        val zeroValves = mutableSetOf<String>()
        for (line in input) {
            val parseResult = parseLine(line)
            val vertex = parseResult.vertex
            vertexes[vertex.name] = vertex
            vertexNeighbors[vertex.name] = parseResult.neighbors
            if (vertex.rate > 0 || vertex.name == "AA") {
                notZeroVertexes[vertex.name] = vertex
            }
        }
        for (vertex in vertexes.values) {
            vertex.edges =
                vertexNeighbors[vertex.name]!!.filter { !zeroValves.contains(it) }.map { vertexes[it]!! }.toSet()
        }

        val graph = mutableMapOf<String, MutableList<Edge>>()
        for (vertex in notZeroVertexes.values) {
            for (otherVertex in notZeroVertexes.values) {
                if (vertex != otherVertex && otherVertex.name != "AA") {
                    val edge = Edge(vertex, otherVertex, dijkstra(vertex, otherVertex, vertexes.values.toList()))
                    if (graph[vertex.name] != null) graph[vertex.name]!!.add(edge) else graph[vertex.name] = mutableListOf(edge)
                }
            }
        }

        DFS(0, graph, "AA", 0, 30, setOf(), false)
        return pressure
    }

    private fun DFS(
        p: Int,
        graph: MutableMap<String, MutableList<Edge>>,
        v: String,
        time: Int,
        maxTime: Int,
        discovered: Set<String>,
        part2: Boolean
    ) {
        pressure = max(pressure, p)

        for (edge in graph[v]!!) {
            val v2 = edge.v2
            val dist = edge.weight
            // if `v2` is not yet discovered
            if (!discovered.contains(v2.name) && time + dist + 1 < maxTime) {
                DFS(
                    p + (maxTime - time - dist - 1) * v2.rate,
                    graph,
                    v2.name,
                    time + dist + 1,
                    maxTime,
                    discovered.union(listOf(v2.name)),
                    part2
                )
            }
        }
        if (part2) {
            DFS(p, graph, "AA", 0, maxTime, discovered, false)
        }
    }

    fun parseLine(line: String): ParseResult {
        val destructuredRegex =
            "Valve ([A-Z]{2}) has flow rate=([0-9]+); tunnels? leads? to valves? (.*)".toRegex()

        return destructuredRegex.matchEntire(line)
            ?.destructured
            ?.let { (name, rate, valves) ->
                ParseResult(RatedVertex(name, rate.toInt()), valves.split(", ").toSet())

            }
            ?: throw IllegalArgumentException("Bad input '$line'")
    }

    fun part2(input: List<String>): Int {
        val vertexes = mutableMapOf<String, RatedVertex>()
        val notZeroVertexes = mutableMapOf<String, RatedVertex>()
        val vertexNeighbors = mutableMapOf<String, Set<String>>()
        val zeroValves = mutableSetOf<String>()
        for (line in input) {
            val parseResult = parseLine(line)
            val vertex = parseResult.vertex
            vertexes[vertex.name] = vertex
            vertexNeighbors[vertex.name] = parseResult.neighbors
            if (vertex.rate > 0 || vertex.name == "AA") {
                notZeroVertexes[vertex.name] = vertex
            }
        }
        for (vertex in vertexes.values) {
            vertex.edges =
                vertexNeighbors[vertex.name]!!.filter { !zeroValves.contains(it) }.map { vertexes[it]!! }.toSet()
        }

        val graph = mutableMapOf<String, MutableList<Edge>>()
        for (vertex in notZeroVertexes.values) {
            for (otherVertex in notZeroVertexes.values) {
                if (vertex != otherVertex && otherVertex.name != "AA") {
                    val edge = Edge(vertex, otherVertex, dijkstra(vertex, otherVertex, vertexes.values.toList()))
                    if (graph[vertex.name] != null) graph[vertex.name]!!.add(edge) else graph[vertex.name] = mutableListOf(edge)
                }
            }
        }

        DFS(0, graph, "AA", 0, 26, setOf(), true)
        return pressure
    }

    private fun dijkstra(startNode: RatedVertex, endNode: RatedVertex, vertexList: List<RatedVertex>): Int {
        val distances = mutableMapOf<RatedVertex, Int>()
        distances[startNode] = 0
        val q = mutableSetOf<RatedVertex>()
        for (vertex in vertexList) {
            if (vertex != startNode) distances[vertex] = Int.MAX_VALUE
            q.add(vertex)
        }

        while (q.isNotEmpty()) {
            val v = q.minBy { distances[it]!! }
            q.remove(v)

            for (neighbor in v.edges) {
                if (q.contains(neighbor)) {
                    val alt = distances[v]!! + 1
                    if (alt < distances[neighbor]!!) {
                        distances[neighbor] = alt
                    }
                }
            }
            if (v == endNode) {
                distances[endNode]!!
            }
        }
        return distances[endNode]!!
    }
}

fun main() {
    val input = readInput("input16_1")
    println(Day16().part1(input))
    println(Day16().part2(input))
}