package jeuDeLaVie

import java.util.*

class GOLRandomBoard {
    companion object {

        fun generate(x: Int, y: Int, z: Int, density: Double = 0.5, boundaries: Boolean = true, seed: Long? = null): GOLBoard {
            val board = if(boundaries) GOLBoard(x,y,z) else GOLBoardNoBoundaries(x,y,z)
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