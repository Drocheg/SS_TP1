package jeuDeLaVie

class GOLBoardNoBoundaries(x: Int, y: Int, z: Int,
                           private val boundX: Boolean = false,
                           private val boundY: Boolean = false,
                           private val boundZ: Boolean = false): GOLBoard(x,y,z) {

    override operator fun get(xIndex: Int, yIndex: Int, zIndex: Int): Int {
        if(boundX && (xIndex<0 || x<=xIndex)) { return 0 }
        if(boundY && (yIndex<0 || y<=yIndex)) { return 0 }
        if(boundZ && (zIndex<0 || z<=zIndex)) { return 0 }
        return board[Math.floorMod(xIndex, x)][Math.floorMod(yIndex, y)][Math.floorMod(zIndex, z)]
    }

    override operator fun set(xIndex: Int, yIndex: Int, zIndex: Int, value: Int) {
        if(boundX && (xIndex<0 || x<=xIndex)) { return }
        if(boundY && (yIndex<0 || y<=yIndex)) { return }
        if(boundZ && (zIndex<0 || z<=zIndex)) { return }
        super.set(Math.floorMod(xIndex, x), Math.floorMod(yIndex, y), Math.floorMod(zIndex, z), value)
    }
}