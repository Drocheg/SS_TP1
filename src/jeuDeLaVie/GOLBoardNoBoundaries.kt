package jeuDeLaVie

class GOLBoardNoBoundaries(x: Int, y: Int, z: Int): GOLBoard(x,y,z) {

    override operator fun get(zIndex: Int, yIndex: Int, xIndex: Int): Int {
        return board[zIndex%z][yIndex%y][xIndex%x]
    }

    override operator fun set(zIndex: Int, yIndex: Int, xIndex: Int, value: Int) {
        board[zIndex%z][yIndex%y][xIndex%x] = value
    }
}