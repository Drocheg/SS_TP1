package jeuDeLaVie

import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.File



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

    fun outputOvito(fileName: String, iteration: Int) {
        try {
            val theFile = File(fileName + iteration)
            BufferedWriter(OutputStreamWriter(
                    FileOutputStream(theFile), "utf-8")).use { writer ->
                writer.write("${board.flatten().toTypedArray().flatten().sum()}\n")
                writer.write("\n")
                for(i in 0 until z) {
                    for(j in 0 until y) {
                        for(k in 0 until x) {
                            if(this[i,j,k] != 0) {
                                writer.write("$k\t$j\t$i\t255\t255\t255\n")
                            } else {
                            }
                        }
                    }
                }
                writer.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //TODO do something with error
        }
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