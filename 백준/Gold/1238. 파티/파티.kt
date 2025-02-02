import kotlin.math.max
import kotlin.math.min

class Road(val cost: Int, val start: Int, val dest: Int)

class House(val roads: MutableList<Road> = mutableListOf())

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
    *
    * */

    val (n, m, x) = readln().split(" ").map { it.toInt() }

    val costs = Array(n) { IntArray(n) { Integer.MAX_VALUE } }

    repeat(m) {
        val (start, dest, cost) = readln().split(" ").map { it.toInt() }
        costs[start - 1][dest - 1] = min(cost, costs[start - 1][dest - 1])
    }

    for (k in 0 until n) {
        for (i in 0 until n) {
            if (k == i) continue
            for (j in 0 until n) {
                if (i == j || k == j) continue

                if (costs[i][k] == Integer.MAX_VALUE || costs[k][j] == Integer.MAX_VALUE)
                    continue
                costs[i][j] = min(costs[i][j], costs[i][k] + costs[k][j])
            }
        }
    }

    var answer = 0

    for (i in 0 until n) {
        if (i == x - 1) continue
        answer = max(answer, costs[i][x - 1] + costs[x - 1][i])
    }
    print(answer)
}