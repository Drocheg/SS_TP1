package jeuDeLaVie

class StandarRules: GOLRule {

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
            oldBoard[z,y,x] != 0 && neighbours<2                    -> newboard[z,y,x] = 0
            oldBoard[z,y,x] != 0 && (neighbours==2 || neighbours==3)  -> newboard[z,y,x] = 1
            oldBoard[z,y,x] != 0 && neighbours>3                    -> newboard[z,y,x] = 0
            oldBoard[z,y,x] == 0 && neighbours==3                   -> newboard[z,y,x] = 1
            else -> newboard[z,y,x] = 0
        }
    }

}