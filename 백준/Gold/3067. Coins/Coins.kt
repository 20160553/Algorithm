import java.io.BufferedReader
import java.io.InputStreamReader

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var T = readLine()!!.toInt();
    var sb = StringBuilder()
    for (test_case in 1..T) {
        var n = readLine()!!.toInt()
        var coins = readLine().split(" ").map { it.toInt() }.toIntArray()
        var m = readLine()!!.toInt()
        var dp = IntArray(m+1)
        for (i in 0 until coins.size) {
            for (j in 1 until dp.size) {
                if (j == coins[i]) dp[j]++
                if(j > coins[i]) dp[j] = dp[j - coins[i]] + dp[j]
            }
        }
        sb.append("${dp[m]}\n")
    }
    print(sb)
}