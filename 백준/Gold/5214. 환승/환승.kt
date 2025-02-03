import java.util.*

class Station(val id: Int) {
    val tubes: MutableSet<Int> = mutableSetOf()
}

fun main() {
    val (n, k, m) = readln().split(" ").map { it.toInt() }
    val stations = Array(n + 1) {
        Station(it)
    }
    var tubes = Array(m + 1) { IntArray(k) }

    val vStation = BooleanArray(n + 1)
    val vTube = BooleanArray(m + 1)

    repeat(m) {
        tubes[it] = readln().split(" ").map { str -> str.toInt() }.toIntArray()
        for (station in tubes[it]) {
            stations[station].tubes.add(it)
        }
    }
    val q = LinkedList<IntArray>()

    q.add(intArrayOf(1, 1))
    vStation[1] = true

    if (1 == n) {
        print(1)
        return
    }

    while (q.isNotEmpty()) {
        val current = q.poll()
        val currentStation = current[0]
        val cnt = current[1]

        for (tube in stations[currentStation].tubes) {
            if (vTube[tube]) continue
            vTube[tube] = true
            for (s in tubes[tube]) {
                if (vStation[s]) continue
                vStation[s] = true
                if (s == n) {
                    print(cnt + 1)
                    return
                }
                q.offer(intArrayOf(s, cnt + 1))
            }
        }
    }

    print(-1)
}