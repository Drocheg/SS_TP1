package jeuDeLaVie

class AgingRule(val other: GOLRule,val age: Int): GOLRule {
    override val name: String
        get() = "AgingRule"

    val map = HashMap<Triple<Int,Int,Int>, Int>()
    override fun modify(oldBoard: GOLBoard, newboard: GOLBoard, x: Int, y: Int, z: Int) {
        other.modify(oldBoard, newboard, x,y,z)

        if(oldBoard[x,y,z] != 0 && newboard[x,y,z] == 0) {
            val position = Triple(x,y,z)
            map[position] = map[position]?.plus(1) ?: 0
            if(map[position]!! >= age) {
                map[position] = 0
            } else {
                newboard[x,y,z] = 1
            }
        }
    }
}