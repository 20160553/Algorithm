import java.io.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n) = br.readLine().split(" ").map {it.toInt()}
    val str = StringBuilder()
    for (i in 1 .. n) {
        for (j in 1 .. n - i) {str.append(" ")}
        repeat(i){str.append("*")}
        str.append("\n")
    }
    print(str)
}