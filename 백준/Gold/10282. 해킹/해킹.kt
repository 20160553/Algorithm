import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer
import kotlin.math.min

lateinit var adj: Array<MutableList<IntArray>>
lateinit var distance: IntArray
var count = 0
var max_distance = 0

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    repeat(readLine()!!.toInt()) {
        var st = StringTokenizer(readLine())
        val n = st.nextToken().toInt()
        val d = st.nextToken().toInt()
        val c = st.nextToken().toInt()
        adj = Array(n + 1) { mutableListOf() }
        distance = IntArray(n + 1) { Int.MAX_VALUE }
        repeat(d) {
            val (a, b, s) = readLine()!!.split(" ").map { it.toInt() }
            adj[b].add(intArrayOf(a, s))
        }
        dijkstra(c)
        count = 0
        max_distance = 0
        for (i in distance) {
            if (i != Int.MAX_VALUE) {
                count += 1
                if (i > max_distance)
                    max_distance = i
            }
        }
        println("$count $max_distance")
    }
}


fun dijkstra(start: Int) {
    val minHeap = PriorityQueue<Pair<Int, Int>>(compareBy { it.first } )
    minHeap.add(Pair(0, start))
    distance[start] = 0
    while (minHeap.isNotEmpty()) {
        val (dist, now) = minHeap.poll()
        if (distance[now] < dist)
            continue
        for (i in adj[now]) {
            val cost = dist + i[1]
            if (distance[i[0]] > cost) {
                distance[i[0]] = cost
                minHeap.add(Pair(cost, i[0]))
            }
        }
    }
}
