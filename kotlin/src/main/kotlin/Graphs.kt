package com.example

import java.util.*
import kotlin.Comparator


data class Edge(val to: Int, val cost: Int)

data class Node(val id: Int, val edges: LinkedList<Edge>)

// Path class tracks the path taken
data class Path(val prev: Path?, val node: Node, val cost: Int) {
    override fun toString(): String {
        // reversing the path
        var path: Path? = this
        val list: LinkedList<Int> = LinkedList<Int>()

        while (path != null) {
            list.add(path.node.id)
            path = path.prev
        }

        val order = list
            .reversed()
            .joinToString(separator = " -> ")

        return "Order: $order Cost: $cost"
    }
}

class Graph(val nodes: Array<Node>) {
    fun djikstra(start: Int, target: Int): Path? {
        val visited: Array<Boolean> = Array(nodes.size, init = {_ -> false})
        val pq: PriorityQueue<Path> = PriorityQueue(compareBy { it.cost })

        // initial path
        val initialPath = Path(null, nodes[start], 0)
        pq.add(initialPath)

        // Dijkstra's algorithm
        while (!pq.isEmpty()) {
            val explore = pq.remove()

            for (edge in explore.node.edges) {
                // check that the node wasn't visited
                if (visited[edge.to]) {
                    continue
                }
                visited[edge.to] = true

                // create a new path
                val node = nodes[edge.to]
                val cost = explore.cost + edge.cost // calculate the total cost
                val path = Path(explore, node, cost)

                if (path.node.id == target) {
                    return path
                }

                pq.add(path)
            }
        }

        return null
    }
}

fun main() {
    val graph = Graph(
        arrayOf(
            Node(0, LinkedList<Edge>(arrayListOf(Edge(1, 5), Edge(2, 2)))),
            Node(1, LinkedList<Edge>(arrayListOf(Edge(2, 5), Edge(3, 5)))),
            Node(2, LinkedList<Edge>(arrayListOf(Edge(4, 1)))),
            Node(3, LinkedList<Edge>(arrayListOf(Edge(4, 2)))),
            Node(4, LinkedList<Edge>(arrayListOf(Edge(0, 4))))
        )
    )
    println(graph.djikstra(0, 4))
    println(graph.djikstra(3, 2))
}