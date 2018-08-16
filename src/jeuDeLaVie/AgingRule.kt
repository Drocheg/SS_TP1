package jeuDeLaVie

import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class AgingRule(val other: GOLRule,val age: Int): GOLRule {
    override val name: String
        get() = "AgingRule"

    val map = HashMap<Triple<Int,Int,Int>, Int>()
    override fun modify(oldBoard: GOLBoard, newboard: GOLBoard, x: Int, y: Int, z: Int) {
        other.modify(oldBoard, newboard, x,y,z)

        if(oldBoard[x,y,z] != 0 && newboard[x,y,z] == 0) {
            val position = Triple(x,y,z)
            map[position] = map[position]?.plus(1) ?: 0
            if(map[position]!! >= age) {
                map[position] = 0
            } else {
                newboard[x,y,z] = 1
            }
        }
    }


    fun printRule(iteration: Int, board: GOLBoard) {
        File("ovitoaging").mkdirs()
        try {
            val theFile = File("ovitoaging/ovitoaging"+ iteration)
            BufferedWriter(OutputStreamWriter(
                    FileOutputStream(theFile), "utf-8")).use { writer ->
                writer.write("${board.board.flatten().toTypedArray().flatten().sum() + 8}\n")
                writer.write("\n")
                for(i in 0 until board.x) {
                    for(j in 0 until board.y) {
                        for(k in 0 until board.z) {
                            if(board[i,j,k] != 0) {
                                val age = map[Triple(i,j,k)] ?: 0

                                val color = age.toDouble()/this.age
                                val col = "${(2*color).clamp(0.0,1.0)}\t${(2*(1-color)).clamp(0.0,1.0)}\t${0}"
                                writer.write("$i\t$j\t$k\t" + col + "\t0.3\n")
                            }
                        }
                    }
                }
                writer.write("${0}\t${0}\t${0}\t0\t0\t0\t0.1\n")
                writer.write("${board.x}\t${0}\t${0}\t0\t0\t0\t0.1\n")
                writer.write("${0}\t${board.y}\t${0}\t0\t0\t0\t0.1\n")
                writer.write("${board.x}\t${board.y}\t${0}\t0\t0\t0\t0.1\n")
                writer.write("${0}\t${0}\t${board.z}\t0\t0\t0\t0.1\n")
                writer.write("${board.x}\t${0}\t${board.z}\t0\t0\t0\t0.1\n")
                writer.write("${0}\t${board.y}\t${board.z}\t0\t0\t0\t0.1\n")
                writer.write("${board.x}\t${board.y}\t${board.z}\t0\t0\t0\t0.1\n")
                writer.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //TODO do something with error
        }
    }
}