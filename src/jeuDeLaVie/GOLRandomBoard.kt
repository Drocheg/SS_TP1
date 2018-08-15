package jeuDeLaVie

import java.util.*

class GOLRandomBoard {
    companion object {

        private val rand = Random(120);

        fun generate(x: Int, y: Int, z: Int, xRadius: Int, yRadius: Int, zRadius: Int, density: Double = 0.5,
                     boundX: Boolean = false, boundY: Boolean = false, boundZ: Boolean = false,
                     seed: Long? = null): GOLBoard {

            val board = GOLBoardNoBoundaries(x,y,z, boundX, boundY, boundZ)

            val centerX = x/2;
            val centerY = y/2;
            val centerZ = z/2;

            for(i in centerX - xRadius + 1 .. centerX + xRadius) {
                for(j in centerY - yRadius + 1 .. centerY + yRadius) {
                    for(k in centerZ - zRadius .. centerZ + zRadius) {
                        board[i,j,k] = if(rand.nextDouble() < density) 1 else 0
                    }
                }
            }

            return board
        }

    }
}