import java.util.LinkedList

fun bfs(arr: Array<BooleanArray>, v: Array<BooleanArray>, y: Int, x: Int): Int {
    val q = LinkedList<IntArray>()
    var resultSize = 0;

    if (!arr[y][x] && !v[y][x]) {
        q.offer(intArrayOf(y, x))
        v[y][x] = true
        resultSize++
    }

    val dy = intArrayOf(-1, 0, 1, 0)
    val dx = intArrayOf(0, -1, 0, 1)

    while (!q.isEmpty()) {
        val (cy, cx) = q.poll()
        for (i in 0 until 4) {
            val ny = cy + dy[i]
            val nx = cx + dx[i]

            if (ny < 0 || nx < 0 || ny >= v.size || nx >= v[0].size) continue
            if (v[ny][nx] || arr[ny][nx]) {
                continue
            }
            v[ny][nx] = true
            q.offer(intArrayOf(ny, nx));
            resultSize++;
        }
    }

    return resultSize;
}

fun main() {
    val (m, n, k) = readln().split(' ').map { it.toInt() }

    val arr = Array(m) { BooleanArray(n) }
    val v = Array(m) { BooleanArray(n)}

    for (i in 0 until k) {
        val (x1, y1, x2, y2) = readln().split(' ').map { it.toInt() }

        for (y in 0 until m) {
            for (x in 0 until n) {
                if (x in x1 until x2 && y1 <= y && y < y2) {
                    arr[y][x] = true;
                }
            }
        }

    }

    val answer = mutableListOf<Int>()
    for (y in 0 until m) {
        for (x in 0 until n) {
            val result = bfs(arr, v, y, x)
            if (result != 0)
                answer.add(result)
        }
    }
    answer.sort()
    println(answer.size)
    answer.forEach { print("$it ")}
}