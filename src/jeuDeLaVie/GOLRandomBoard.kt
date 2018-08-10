package jeuDeLaVie

import java.util.*

class GOLRandomBoard {
    companion object {

        fun generate(x: Int, y: Int, z: Int, density: Double = 0.5,
                     boundX: Boolean = false, boundY: Boolean = false, boundZ: Boolean = false,
                     seed: Long? = null): GOLBoard {

            val board = GOLBoardNoBoundaries(x,y,z, boundX, boundY, boundZ)
            val rand = if(seed != null)  Random(seed) else Random()

            for(i in 0 until z) {
                for(j in 0 until y) {
                    for(k in 0 until x) {
                        board[i,j,k] = if(rand.nextFloat() < density) 1 else 0
                    }
                }
            }

            return board
        }

    }
}