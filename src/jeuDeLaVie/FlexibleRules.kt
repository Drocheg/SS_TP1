package jeuDeLaVie


open class FlexibleRules(override val name: String, var deadInLife: BooleanArray, val lifeInDead: BooleanArray) : GOLRule {

    override fun modify(oldBoard: GOLBoard, newboard: GOLBoard, x: Int, y: Int, z: Int) {
        var neighbours = 0
        for(i in -1 until 2) {
            for (j in -1 until 2) {
                for(k in -1 until 2) {
                    if(oldBoard[x + i, y + j, z + k] != 0) {
                        neighbours++
                    }
                }
            }
        }

        if(oldBoard[x,y,z] != 0) { neighbours-- }
        newboard[x,y,z] = oldBoard[x,y,z]
        if(oldBoard[x,y,z] != 0){
            if(deadInLife[neighbours]){
                newboard[x,y,z] = 0
            }
        }else{
            if(lifeInDead[neighbours]){
                newboard[x,y,z] = 1
            }
        }

    }

}