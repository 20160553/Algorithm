import java.util.*

fun main() {
    val (n, k) = readln().split(" ").map { it.toInt() }

    val MAX_POSITION = 100_000
    val q: Queue<IntArray> = LinkedList()
    val v: IntArray = IntArray(MAX_POSITION + 1) { -1 }

    val functions: List<(Int) -> Int> = listOf(
            { x -> 2 * x },
            { x -> x - 1 },
            { x -> x + 1 }
    )
    q.offer(intArrayOf(n, 0))
    v[n] = 0

    while (!q.isEmpty()) {
        val current = q.poll()
        if (current[0] == k) {
            print(current[1])
            return
        }
        for (j in functions.indices) {
            val next: IntArray = current.copyOf()
            next[0] = functions[j](current[0])

            if (next[0] > MAX_POSITION || next[0] < 0) continue
            if (j != 0) next[1]++

            if (next[0] == k) {
                print(next[1])
                return
            }

            if (v[next[0]] == -1 || v[next[0]] > next[1]) {
                v[next[0]] = next[1]
                q.offer(next)
            }
        }
    }
}