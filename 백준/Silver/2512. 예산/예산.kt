import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    /*
    * 1초 -> 1억 이하
    * 128MB
    *
    * N <= 1만
    * 예산 <= 100만
    *
    * 완탐 => 10 ^ 6 * 10^4 => 10 ^ 10
    *
    * 이진탐색?
    *
    * */

    val input = BufferedReader(InputStreamReader(System.`in`))
    val n = input.readLine().toInt()
    var l = 0
    var r = 0
    val arr = input.readLine().split(" ").map {
        val num = it.toInt()
        r = Math.max(num, r)
        num
    }

    val LIMIT = input.readLine().toInt()
    var answer = 0
    while (l <= r) {
        val m = (l + r) / 2
        var sum = 0
        for (i in arr) {
            if (i > m) {
                sum += m
            } else
                sum += i
        }
        if (sum <= LIMIT) {
            l = m + 1
            answer = m
        }
        else
            r = m - 1
    }

    println(answer)

}