package jeuDeLaVie

open class GOLBoard(val x: Int, val y: Int, val z: Int) {

    protected val board: Array<Array<Array<Int>>> = Array(z) { Array(y) { Array(x) { 0 } } }

    open operator fun get(zIndex: Int, yIndex: Int, xIndex: Int): Int {
        if(zIndex < 0 || yIndex < 0 || xIndex < 0 ) { return 0 }
        if(zIndex>=z || yIndex>=y || xIndex>=x) { return 0 }
        return board[zIndex][yIndex][xIndex]
    }

    open operator fun set(zIndex: Int, yIndex: Int, xIndex: Int, value: Int) {
        if(zIndex < 0 || yIndex < 0 || xIndex < 0 ) { return }
        if(zIndex>=z || yIndex>=y || xIndex>=x) { return }
        board[zIndex][yIndex][xIndex] = value
    }

    override fun toString(): String {
        val boardString = StringBuilder()
        for(i in 0 until z) {
            for (j in 0 until y) {
                for (k in 0 until x) {
                    boardString.append(this[i,j,k])
                }
                boardString.append("\n")
            }
            boardString.append("\n")
        }

        return boardString.toString()
    }

}