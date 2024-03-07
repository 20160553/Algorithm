fun bfs(map: Array<Array<Char>>, v: Array<Array<Array<Boolean>>>, sy: Int, sx: Int): Int {

    val q: MutableList<Array<Int>> = mutableListOf()

    v[0][sy][sx] = true
    //key fedcba
    q.add(arrayOf(0, sy, sx))

    val dy = arrayOf(-1, 0, 1, 0)
    val dx = arrayOf(0, -1, 0, 1)

    var cnt = 1

    while(q.isNotEmpty()) {
        val qSize = q.size
        repeat(qSize) {
            val current = q.removeFirst()
            val key = current[0]
            val cy = current[1]
            val cx = current[2]

            for (i in dy.indices) {
                val ny = cy + dy[i]
                val nx = cx + dx[i]
                var nKey = key
                //범위 밖이면 건너뛰기
                if (ny < 0 || nx < 0 || ny >= map.size || nx >= map[0].size)
                    continue
                //방문했던 곳이거나 벽일 경우 건너뛰기
                if (v[key][ny][nx] || map[ny][nx] == '#')
                    continue

                //방문 처리
                when(val next = map[ny][nx]) {
                    '1' -> {
                        return cnt
                    }
                    in ('a' .. 'f') -> {
                        nKey = nKey or 1.shl(next - 'a')
                    }
                    in ('A' .. 'F') -> {
                        val shifted = 1.shl(next - 'A')
                        if (nKey and shifted != shifted)
                            continue
                    }
                }
                v[key][ny][nx] = true
                v[nKey][ny][nx] = true
                q.add(arrayOf(nKey, ny, nx))
            }

        }
        cnt++
    }

    return -1
}

fun main() {
    val (n, m) = readln().split(' ').map { it.toInt() }
    val map: Array<Array<Char>> = Array(n) { Array(m) { '.' } }

    var sy: Int = 0
    var sx: Int = 0

    for (y in 0 until n) {
        val st = readln()
        for (x in 0 until m) {
            map[y][x] = st.get(x)
            if (map[y][x] == '0') {
                sy = y
                sx = x
            }
        }
    }

    var v: Array<Array<Array<Boolean>>> = Array(64) { Array(n) { Array(m) { false } } }

    print(bfs(map, v, sy, sx))

}