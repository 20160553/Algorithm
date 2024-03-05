import kotlin.math.min

fun main() {

    val n: Int = readln().toInt() ?: 0
    val m: Int = readln().toInt() ?: 0

    val cities = Array(n + 1) { i ->
        IntArray(n + 1) { j ->
            if (i == j)
                return@IntArray 0
            Int.MAX_VALUE
        }
    }

    repeat(m) {
        val (start, end, cost) = readln().split(' ').map { it.toInt() }
        cities[start][end] = min(cities[start][end], cost)
    }

    for (k in (1..n)) {
        for (i in (1..n)) {
            if (k == i) continue
            if (cities[i][k] == Int.MAX_VALUE) continue
            for (j in (1..n)) {
                if (k == j || i == j) continue
                if (cities[k][j] == Int.MAX_VALUE) continue
                cities[i][j] = min(cities[i][j], cities[i][k] + cities[k][j])
            }
        }
    }

    val sb: StringBuilder = StringBuilder()

    for (i in (1 .. n)) {
        for (j in (1 .. n)) {
            if (cities[i][j] == Int.MAX_VALUE)
                cities[i][j] = 0
            sb.append(cities[i][j]).append(" ")
        }
        sb.append("\n")
    }

    print(sb)
}