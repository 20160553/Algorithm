fun main() {
    val n = readln().toInt()
    
    var price = readln().toInt()
    repeat(n - 1) {
        price += readln().toInt()
    }
    print(price)
}