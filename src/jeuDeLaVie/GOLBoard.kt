package jeuDeLaVie

import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.File
import java.util.*


open class GOLBoard(val x: Int, val y: Int, val z: Int, var count: Int = 0) {
    private constructor(other: GOLBoard) : this(other.x, other.y, other.z, other.count) {
        for(i in 0 until x)
            for(j in 0 until y)
                for(k in 0 until z) {
                    board[i][j][k] = other.board[i][j][k]
                }

    }

    open fun clone() = GOLBoard(this)

    protected val board: Array<Array<Array<Int>>> = Array(x) { Array(y) { Array(z) { 0 } } }

    open operator fun get(xIndex: Int, yIndex: Int, zIndex: Int): Int {
        if(zIndex < 0 || yIndex < 0 || xIndex < 0 ) { return 0 }
        if(zIndex>=z || yIndex>=y || xIndex>=x) { return 0 }
        return board[xIndex][yIndex][zIndex]
    }

    open operator fun set(xIndex: Int, yIndex: Int, zIndex: Int, value: Int) {
        if(zIndex < 0 || yIndex < 0 || xIndex < 0 ) { return }
        if(zIndex>=z || yIndex>=y || xIndex>=x) { return }
        count = count - get(xIndex, yIndex, zIndex) + value
        board[xIndex][yIndex][zIndex] = value
    }

    fun ovitoSave(iteration: Int, centerMass: Coordinate?, maxDistance: Int) {
        if(centerMass == null) {
            ovitoBW(iteration, "ovitoDistance")
        } else {
            ovitoDistance(iteration, centerMass, maxDistance)
        }
    }

    fun ovitoBW(iteration: Int, folder: String) {
        outputOvito(8,iteration, folder, { _,_,_ -> "255\t255\t255" }) {
            writeBorder(it)
        }
    }

    fun ovitoDistance(iteration: Int, fromPoint: Coordinate, maxDistance: Int) {
        outputOvito(9, iteration, "ovitoDistance", { x,y,z ->
            val distance = Math.sqrt(
                    ((fromPoint.x - x) * (fromPoint.x - x) +
                    (fromPoint.y - y) * (fromPoint.y - y) +
                    (fromPoint.z - z) * (fromPoint.z - z)).toDouble()
            )

            val color = distance/maxDistance
            "${(2*color).clamp(0.0,1.0)}\t${(2*(1-color)).clamp(0.0,1.0)}\t${0}"
        }) {
            it.write("${fromPoint.x}\t${fromPoint.y}\t${fromPoint.z}\t1\t1\t1\t0.5\n")
            writeBorder(it)
        }
    }

    private fun writeBorder(writer: BufferedWriter) {
        writer.write("${0}\t${0}\t${0}\t0\t0\t0\t0.1\n")
        writer.write("${x}\t${0}\t${0}\t0\t0\t0\t0.1\n")
        writer.write("${0}\t${y}\t${0}\t0\t0\t0\t0.1\n")
        writer.write("${x}\t${y}\t${0}\t0\t0\t0\t0.1\n")
        writer.write("${0}\t${0}\t${z}\t0\t0\t0\t0.1\n")
        writer.write("${x}\t${0}\t${z}\t0\t0\t0\t0.1\n")
        writer.write("${0}\t${y}\t${z}\t0\t0\t0\t0.1\n")
        writer.write("${x}\t${y}\t${z}\t0\t0\t0\t0.1\n")
    }

    fun ovitoCenterMass(iteration: Int, centerMass: Coordinate) {
        outputOvito(1, iteration, "ovitoCenterMass",{_,_,_ -> "1\t1\t1"}) {
            it.write("${centerMass.x}\t${centerMass.y}\t${centerMass.z}\t1\t0\t0\t1.0\n")
        }
    }

    private fun outputOvito(extraLines: Int = 0,
                            iteration: Int,
                            folder: String,
                            toColor: (Int, Int, Int) -> String,
                            after: (BufferedWriter) -> Unit = {}) {

        File(folder).mkdirs()
        try {
            val theFile = File(folder + "/" + folder + iteration)
            BufferedWriter(OutputStreamWriter(
                    FileOutputStream(theFile), "utf-8")).use { writer ->
                writer.write("${board.flatten().toTypedArray().flatten().sum() + extraLines}\n")
                writer.write("\n")
                for(i in 0 until x) {
                    for(j in 0 until y) {
                        for(k in 0 until z) {
                            if(this[i,j,k] != 0) {
                                val col = toColor(i,j,k)
                                writer.write("$i\t$j\t$k\t" + col + "\t0.3\n")
                            }
                        }
                    }
                }
                after(writer)
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

fun Double.clamp(min: Double, max: Double): Double = Math.max(min, Math.min(this, max))
