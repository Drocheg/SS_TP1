package jeuDeLaVie

open class GenericRules(var r1: Int, val r2: Int, val r3: Int, val r4: Int) : GOLRule {

    override fun modify(oldBoard: GOLBoard, newboard: GOLBoard, x: Int, y: Int, z: Int) {
        var neighbours = 0
        for(i in -1 until 2) {
            for (j in -1 until 2) {
                for(k in -1 until 2) {
                    if(oldBoard[z + i, y + j, x + k] != 0) {
                        neighbours++
                    }
                }
            }
        }

        if(oldBoard[z,y,x] != 0) { neighbours-- }

        when {
            neighbours<r3           -> newboard[z,y,x] = 0
            neighbours>r4           -> newboard[z,y,x] = 0
            (neighbours in r1..r2)  -> newboard[z,y,x] = 1
            else -> newboard[z,y,x] = oldBoard[z,y,x]
        }
    }

}