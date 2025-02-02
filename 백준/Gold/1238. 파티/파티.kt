import java.util.*
import kotlin.math.max
import kotlin.math.min

fun dijkstra(start: Int, arr: Array<IntArray>) {
    val pq: PriorityQueue<IntArray> = PriorityQueue { o1, o2 ->
        return@PriorityQueue o1[1] - o2[1]
    }

    val v = BooleanArray(arr.size)

    for (i in arr.indices) {
        if (arr[start][i] != Integer.MAX_VALUE)
        pq.add(intArrayOf(i, arr[start][i]))
    }

    while (!pq.isEmpty()) {
        val current = pq.poll()
        v[current[0]] = true
        for (i in arr.indices) {
            if (v[i]) continue
            if (i == current[0] || i == start) continue
            if (arr[current[0]][i] == Integer.MAX_VALUE) continue
            if (arr[start][i] > current[1] + arr[current[0]][i]) {
                arr[start][i] = current[1] + arr[current[0]][i]
                pq.add(intArrayOf(i, arr[start][i]))
            }
        }
    }
}

fun main() {
    /*
    * 1<= N <= 1_000
    * 1 <= M <= 10_000
    *
    * 최단거리
    *
    * 모든 곳 => 모든 곳
    *
    * 제한시간 : 1초 => 10 ^ 8 미만
    * 메모리제한 : 128MB => 128 * 10 ^ 6 Byte => 32 * 10 ^ 6 INT
    *
    * 플로이드 워샬 N * N * N => 10 ^ 9
    *   => 이거 왜 통과? 흠...
    *
    * */

    val (n, m, x) = readln().split(" ").map { it.toInt() }

    val costs = Array(n) { IntArray(n) { Integer.MAX_VALUE } }
    val reverseCosts = Array(n) { IntArray(n) { Integer.MAX_VALUE } }

    repeat(m) {
        val (start, dest, cost) = readln().split(" ").map { it.toInt() }
        costs[start - 1][dest - 1] = min(cost, costs[start - 1][dest - 1])
        reverseCosts[dest - 1][start - 1] = min(cost, reverseCosts[dest - 1][start - 1])
    }

    dijkstra(x - 1, costs)
    dijkstra(x - 1, reverseCosts)

    var answer = 0

    for (i in 0 until n) {
        if (i == x - 1) continue
        answer = max(answer, costs[x - 1][i] + reverseCosts[x - 1][i])
    }
    print(answer)
}