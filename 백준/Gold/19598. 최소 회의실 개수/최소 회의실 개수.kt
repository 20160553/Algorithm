import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    /*
    * 2억 이하 연산
    * n <= 10 ^ 5
    * 2 ^ 32 보다 작다 => 정수형 범위
    *
    * 1. 정렬 문제 => NLogN => 10 ^ 5 * 5
    *   i. 시작시간 우선, 끝나는 시간 오름차순
    *
    *
    * */

    val br: BufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()

    val roomList = PriorityQueue<Int>()

    val mettings: PriorityQueue<IntArray> = PriorityQueue { o1, o2 ->
        if (o1[0] == o2[0]) {
            return@PriorityQueue o1[1] - o2[1]
        }
        o1[0] - o2[0]
    }

    for (i in 0 until n) {
        mettings.add(br.readLine().split(" ").map { it.toInt() }.toIntArray())
    }

    mettingLoop@while (mettings.isNotEmpty()) {
        val currentMetting = mettings.poll()

        for (i in 0 until roomList.size) {
            if (roomList.peek() <= currentMetting[0]) {
                roomList.poll()
                roomList.add(currentMetting[1])
                continue@mettingLoop
            }
        }
        roomList.add(currentMetting[1])
    }

    print(roomList.size)
}