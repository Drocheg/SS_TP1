package jeuDeLaVie

import com.sun.org.apache.xpath.internal.operations.Bool

class GOLBoardNoBoundaries(x: Int, y: Int, z: Int,
                           private val boundX: Boolean = false,
                           private val boundY: Boolean = false,
                           private val boundZ: Boolean = false): GOLBoard(x,y,z) {

    override operator fun get(zIndex: Int, yIndex: Int, xIndex: Int): Int {
        if(boundX && (xIndex<0 || x>=xIndex)) { return 0 }
        if(boundY && (yIndex<0 || y>=yIndex)) { return 0 }
        if(boundZ && (zIndex<0 || z>=zIndex)) { return 0 }
        return board[zIndex%z][yIndex%y][xIndex%x]
    }

    override operator fun set(zIndex: Int, yIndex: Int, xIndex: Int, value: Int) {
        if(boundX && (xIndex<0 || x>=xIndex)) { return }
        if(boundY && (yIndex<0 || y>=yIndex)) { return }
        if(boundZ && (zIndex<0 || z>=zIndex)) { return }
        board[zIndex%z][yIndex%y][xIndex%x] = value
    }
}