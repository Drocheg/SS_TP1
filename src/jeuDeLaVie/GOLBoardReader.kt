package jeuDeLaVie

import java.util.*
import java.io.File



class GOLBoardReader {
    companion object {

        fun generate(fileName: String, boundX: Boolean = false, boundY: Boolean = false, boundZ: Boolean = false): GOLBoard {

            val file = File(fileName)
            val scan = Scanner(file)

            val xBound = scan.nextInt()
            val yBound = scan.nextInt()
            val zBound = scan.nextInt()
            val n = scan.nextInt()
            val board = GOLBoardNoBoundaries(xBound,yBound,zBound, boundX, boundY, boundZ)

            for(i in 0 until n){
                val x = scan.nextInt()
                val y = scan.nextInt()
                val z = scan.nextInt()
                board[x, y, z] = scan.nextInt()
            }

            return board
        }

    }
}