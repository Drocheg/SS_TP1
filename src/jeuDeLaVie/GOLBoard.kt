package jeuDeLaVie

import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.File
import java.util.*


open class GOLBoard(val x: Int, val y: Int, val z: Int) {

    protected val board: Array<Array<Array<Int>>> = Array(x) { Array(y) { Array(z) { 0 } } }

    open operator fun get(xIndex: Int, yIndex: Int, zIndex: Int): Int {
        if(zIndex < 0 || yIndex < 0 || xIndex < 0 ) { return 0 }
        if(zIndex>=z || yIndex>=y || xIndex>=x) { return 0 }
        return board[xIndex][yIndex][zIndex]
    }

    open operator fun set(xIndex: Int, yIndex: Int, zIndex: Int, value: Int) {
        if(zIndex < 0 || yIndex < 0 || xIndex < 0 ) { return }
        if(zIndex>=z || yIndex>=y || xIndex>=x) { return }
        board[xIndex][yIndex][zIndex] = value
    }

    fun ovitoBW(iteration: Int) {
        outputOvito(iteration, "ovitoBW") { x,y,z -> "255\t255\t255" }
    }

    fun ovitoDistance(iteration: Int, fromPoint: Triple<Int, Int, Int>) {
        outputOvito(iteration, "ovitoDistance") { x,y,z ->
            val distance = Math.floor(Math.sqrt(
                    ((fromPoint.first - x) * (fromPoint.first - x) +
                            (fromPoint.second - y) * (fromPoint.second - y) +
                            (fromPoint.third - z) * (fromPoint.third - z)).toDouble()
            ))

            val color = (distance/(this.x*2) * 255).toInt()

            "${Random().nextInt(255)}\t${Random().nextInt(255)}\t${Random().nextInt(255)}"
        }
    }

    private fun outputOvito(iteration: Int,folder: String, toColor: (Int, Int, Int) -> String) {
        File(folder).mkdirs()
        try {
            val theFile = File(folder + "/" + folder + iteration)
            BufferedWriter(OutputStreamWriter(
                    FileOutputStream(theFile), "utf-8")).use { writer ->
                writer.write("${board.flatten().toTypedArray().flatten().sum()}\n")
                writer.write("\n")
                for(i in 0 until x) {
                    for(j in 0 until y) {
                        for(k in 0 until z) {
                            if(this[i,j,k] != 0) {
                                val col = toColor(i,j,k)
                                writer.write("$i\t$j\t$k\t" + col + "\n")
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
        for(i in 0 until x) {
            for (j in 0 until y) {
                for (k in 0 until z) {
                    boardString.append(this[i,j,k])
                }
                boardString.append("\n")
            }
            boardString.append("\n")
        }

        return boardString.toString()
    }

}