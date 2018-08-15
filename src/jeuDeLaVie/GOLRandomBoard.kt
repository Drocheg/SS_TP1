package jeuDeLaVie

import java.util.*

class GOLRandomBoard {
    companion object {

        fun generate(x: Int, y: Int, z: Int, xRadius: Int, yRadius: Int, zRadius: Int, density: Double = 0.5,
                     boundX: Boolean = false, boundY: Boolean = false, boundZ: Boolean = false,
                     seed: Long? = null): GOLBoard {

            val board = GOLBoardNoBoundaries(x,y,z, boundX, boundY, boundZ)
            val rand = if(seed != null)  Random(seed) else Random()

            val centerX = x/2;
            val centerY = y/2;
            val centerZ = z/2;

            var ct = 0;

            for(i in centerX - xRadius + 1 .. centerX + xRadius) {
                for(j in centerY - yRadius + 1 .. centerY + yRadius) {
                    for(k in centerZ - zRadius .. centerZ + zRadius) {
                        board[i,j,k] = if(rand.nextDouble() < density) 1 else 0
                        ct++;
                    }
                }
            }

            println("Count $ct");

            return board
        }

    }
}