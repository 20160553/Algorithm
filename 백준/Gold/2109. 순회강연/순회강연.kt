import java.util.*

class College(val id: Int, val period: Int, val pay: Int): Comparable<College> {

    override fun compareTo(other: College): Int {
        if (this.pay == other.pay) {
            return this.period.compareTo(other.period)
        }
        return other.pay.compareTo(this.pay)
    }
}

fun main() {
    val n = readln().toInt()
    var pq = PriorityQueue<College>()

    repeat(n) {
        val (pay, period) = readln().split(" ").map { it.toInt() }

        pq.add(College(it + 1, period, pay))
    }

    var answer = 0
    var v = BooleanArray(10_001)

    while (pq.isNotEmpty()) {
        val current = pq.poll()

        for (i in current.period downTo 1) {
            if (!v[i]) {
                v[i] = true
                answer += current.pay
                break
            }
        }
    }

    print(answer)
}