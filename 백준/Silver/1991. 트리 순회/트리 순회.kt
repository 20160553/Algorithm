class Node(val id: Char, var left: Node? = null, var right: Node? = null) {

    fun preOrder(): String {

        var result = "$id"

        if (left != null) {
            result += left!!.preOrder()
        }
        if (right != null) {
            result += right!!.preOrder()
        }
        return result
    }

    fun inOrder(): String {

        var result = ""

        if (left != null) {
            result += left!!.inOrder()
        }
        result += id
        if (right != null) {
            result += right!!.inOrder()
        }
        return result
    }

    fun postOrder(): String {

        var result = ""

        if (left != null) {
            result += left!!.postOrder()
        }
        if (right != null) {
            result += right!!.postOrder()
        }
        result += id
        return result
    }

}

fun main() {
    val n = readln().toInt()

    val nodes = mutableMapOf<Char, Node>()

    //트리 생성
    repeat(n) {
        val inputs = readln().split(" ").map { it[0] }

        var current: Node? = nodes[inputs[0]]
        var left: Node? = nodes[inputs[1]]
        var right: Node? = nodes[inputs[2]]

        if (inputs[1] != '.' && left == null) {
            left = Node(inputs[1])
            nodes[inputs[1]] = left
        }

        if (inputs[2] != '.' && right == null) {
            right = Node(inputs[2])
            nodes[inputs[2]] = right
        }

        if (current == null) {
            current = Node(inputs[0], left, right)
            nodes[inputs[0]] = current
        } else {
            current.left = left
            current.right = right
        }

    }

    println(nodes['A']?.preOrder())
    println(nodes['A']?.inOrder())
    println(nodes['A']?.postOrder())
}