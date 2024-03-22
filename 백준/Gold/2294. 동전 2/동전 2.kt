import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    var (n, k) = readln().split(" ").map { it.toInt() }
    val coins = HashSet<Int>()

    repeat(n) {
        coins.add(readln().split(" ").map { it.toInt() }[0])
    }

    val dp = MutableList<Int>(k + 1) { Int.MAX_VALUE }

    for (coin in coins) {
        if (coin <= k)
            dp[coin] = 1
    }

    for (coin in coins) {
        for (i in 1..k - coin) {
            if (dp[i] == Int.MAX_VALUE) continue
            dp[i + coin] = dp[i + coin].coerceAtMost(dp[i] + 1)
        }
    }

    if (dp[k] == Int.MAX_VALUE)
        dp[k] = -1
    print(dp[k])

}