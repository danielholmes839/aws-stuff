package com.example

import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap


data class Edge(val to: String, val cost: Int)

data class Node(val name: String, val edges: LinkedList<Edge>)

// Path class tracks the path taken
data class Path(val prev: Path?, val node: Node, val cost: Int) {
    override fun toString(): String {
        // reversing the path
        var path: Path? = this
        val list = LinkedList<String>()

        while (path != null) {
            list.add(path.node.name)
            path = path.prev
        }

        val order = list
            .reversed()
            .joinToString(separator = " -> ")

        return "Order: $order Cost: $cost"
    }
}

class Graph(private val nodes: Map<String, Node>) {
    fun dijkstra(start: String, target: String): Path? {
        val visited = HashMap<String, Boolean>()

        for (name in nodes.keys) {
            visited[name] = false
        }

        val pq: PriorityQueue<Path> = PriorityQueue(compareBy { it.cost })

        // initial path
        val initialPath = Path(null, getNode(start), 0)
        pq.add(initialPath)

        // Dijkstra's algorithm
        while (!pq.isEmpty()) {
            val explore = pq.remove()
            visited[explore.node.name] = true

            if (explore.node.name == target) {
                return explore
            }

            for (edge in explore.node.edges) {
                // check that the node wasn't visited
                if (visited[edge.to] == true) {
                    continue
                }

                // create a new path
                val node = getNode(edge.to)
                val cost = explore.cost + edge.cost // calculate the total cost
                val path = Path(explore, node, cost)

                pq.add(path)
            }
        }

        return null
    }

    fun getNode(name: String): Node {
        return nodes[name] ?: throw Exception("Invalid name: $name")
    }
}

fun initialize(matrix: Array<IntArray>, names: Array<String>): Graph {
    val nodes = HashMap<String, Node>()

    for ((i, weights) in matrix.withIndex()) {
        val name = names[i]
        val edges = LinkedList<Edge>()

        // add edges
        for ((j, weight) in weights.withIndex()) {
            if (weight == 0) continue
            val to = names[j]
            edges.add(Edge(to, weight))
        }

        // add the node to the map
        val node = Node(name, edges)
        nodes[name] = node
    }

    return Graph(nodes)
}

fun main() {
    // Computerphile video
    // https://youtu.be/GazC3A4OQTE?t=82

    val names = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "S")
    val matrix = arrayOf(
        //         A  B  C  D  E  F  G  H  I  J  K  L, S
        intArrayOf(0, 3, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 7), // A
        intArrayOf(3, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 2), // B
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3), // C
        intArrayOf(4, 4, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0), // D
        intArrayOf(0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 5, 0, 0), // E
        intArrayOf(0, 0, 0, 5, 0, 0, 0, 3, 0, 0, 0, 0, 0), // F
        intArrayOf(0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0), // G
        intArrayOf(0, 1, 0, 0, 0, 3, 2, 0, 0, 0, 0, 0, 0), // H
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 4, 4, 0), // I
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 4, 4, 0), // J
        intArrayOf(0, 0, 0, 0, 5, 0, 0, 0, 4, 4, 0, 0, 0), // K
        intArrayOf(0, 0, 2, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0), // L
        intArrayOf(7, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // S
    )

    val graph = initialize(matrix, names)
    println(graph.dijkstra("S", "E"))


    // Abdul Bari video example
    // https://youtu.be/XB4MIexjvY0?t=403

//    val names = arrayOf("1", "2", "3", "4", "5", "6")
//    val matrix = arrayOf(
//        //         1  2  3  4  5  6
//        intArrayOf(0, 2, 4, 0, 0, 0), // 1
//        intArrayOf(2, 0, 1, 7, 0, 0), // 2
//        intArrayOf(4, 1, 0, 0, 3, 0), // 3
//        intArrayOf(0, 7, 0, 0, 2, 1), // 4
//        intArrayOf(0, 0, 3, 2, 0, 5), // 5
//        intArrayOf(0, 0, 0, 1, 5, 0), // 6
//    )
//
//    val graph = initialize(matrix, names)
//    println(graph.dijkstra("1", "4"))
}