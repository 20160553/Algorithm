fun main() {
    var answer = 0
    var min = 61

    val n = readln().toInt()
    val p = readln().split(" ").map {
        val num = it.toInt()
        if (min > num) min = num
        num
    }
    val m = readln().toInt()

    val dp = Array(m / min + 2) {
        Array(m + 1) {
            mutableListOf<Int>()
        }
    }

    for (i in 1 until dp.size) {
        for (j in 0..m) {
            for (k in 0 until n) {
                val next = j + p[k]
                if (next > m)
                    continue

                var nextNum = mutableListOf<Int>()
                nextNum.addAll(dp[i - 1][j])
                if (nextNum.size == 1 && nextNum[0] == 0 && k == 0) continue
                nextNum.add(k)
                nextNum.sortDescending()

                if (dp[i][next].size < nextNum.size) {
                    dp[i][next] = nextNum
                } else if (dp[i][next].size < nextNum.size) {
                    continue
                } else {
                    for (l in 0 until nextNum.size) {
                        if (nextNum[l] > dp[i][next][l])
                            dp[i][next] = nextNum
                        else if (nextNum[l] < dp[i][next][l])
                            continue;
                    }
                }
            }
        }
    }

    var max = mutableListOf<Int>()
    for (i in 0..m) {
        val current = dp[dp.size - 1][i]
        if (current.size > max.size) {
            max = current
        } else if (current.size < max.size) {
            continue
        } else {
            for (l in 0 until max.size) {
                if (current[l] > max[l])
                    max = current
                else if (current[l] < max[l])
                    continue
            }
        }
    }

    var sb = StringBuilder()

    for (l in 0 until max.size) {
        sb.append(max[l])
    }

    print(sb)
}