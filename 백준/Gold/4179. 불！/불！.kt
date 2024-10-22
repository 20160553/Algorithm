import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {

    /*
    * r, c, <= 1000
    * 지훈, 불 -> 상하좌우 이동 가능
    *
    * BFS이긴 함
    *
    * 1. 지훈이 이동
    *  1- 1) 탈출 가능?
    * 2. 불 번짐
    *
    * */

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (r, c) = br.readLine().split(" ").map { it.toInt() }
    val maze = Array(r) { CharArray(c) }

    val peopleQueue = mutableListOf<IntArray>()
    val fireQueue = mutableListOf<IntArray>()

    //초기화
    for (i in 0 until r) {
        val str = br.readLine()
        for (j in 0 until c) {
            maze[i][j] = str[j]
            if (maze[i][j] == 'J')
                peopleQueue.add(intArrayOf(i, j))
            if (maze[i][j] == 'F')
                fireQueue.add(intArrayOf(i, j))
        }
    }

    var day = -1

    val dy = intArrayOf(-1, 0, 1, 0)
    val dx = intArrayOf(0, 1, 0, -1)

    //BFS
    bfs@ while (peopleQueue.isNotEmpty()) {
        val peoplePositionSize = peopleQueue.size
        val fireSize = fireQueue.size
        // 지훈이 이동
        for (i in 0 until peoplePositionSize) {
            val (y, x) = peopleQueue.removeFirst()
            val current = maze[y][x]

            //불에 잡혔음?
            if (current == 'F') continue
            //탈출 가능?
            if (x == 0 || y == 0 || y == r - 1 || x == c - 1) {
                day *= -1
                break@bfs
            }

            //다음 경우의 수
            for (j in dy.indices) {
                val nextY = y + dy[j]
                val nextX = x + dx[j]

                //범위 판정
                if (nextY < 0 || nextX < 0 || nextY >= r || nextX >= c) continue
                val next = maze[nextY][nextX]
                if (next != '.') continue
                maze[nextY][nextX] = 'J'
                peopleQueue.add(intArrayOf(nextY, nextX))
            }
        }

        // 불 번짐
        for (i in 0 until fireSize) {
            val (y, x) = fireQueue.removeFirst()
            for (j in dy.indices) {
                val nextY = y + dy[j]
                val nextX = x + dx[j]

                //범위 판정
                if (nextY < 0 || nextX < 0 || nextY >= r || nextX >= c) continue
                val next = maze[nextY][nextX]
                if (next == '#' || next == 'F') continue
                maze[nextY][nextX] = 'F'
                fireQueue.add(intArrayOf(nextY, nextX))
            }
        }

        // 날짜 갱신
        day--
    }

    if (day < 0) print("IMPOSSIBLE")
    else print(day)
}