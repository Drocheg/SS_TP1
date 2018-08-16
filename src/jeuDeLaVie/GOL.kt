package jeuDeLaVie

import java.lang.System.exit
import kotlin.math.max
import kotlin.system.exitProcess

class GOL {

    companion object {

        fun run(
                initial: GOLBoard,
                epochs: Int,
                rule: GOLRule,
                save: Boolean = false,
                maxDistance: Int = 25) : GOLMetrics {
            var oldBoard = initial
            var newboard = GOLBoard(oldBoard)

            val metrics = GOLMetrics()
            metrics.feed(newboard)
            if (save) {
                val centerMass = metrics.centersOfMass.last()
                newboard.ovitoSave(0,null, maxDistance)
            }
            for (e in 1 .. epochs) {
                val aux = oldBoard
                oldBoard = newboard
                newboard = aux

                for (i in 0 until oldBoard.x) {
                    for (j in 0 until oldBoard.y) {
                        for (k in 0 until oldBoard.z) {
                            rule.modify(oldBoard, newboard, i, j, k)
                        }
                    }
                }

                metrics.feed(newboard)
                if (save) {
                    val centerMass = metrics.centersOfMass.last()
                    newboard.ovitoSave(e,null, maxDistance)
                }
            }
            return metrics
        }

            @JvmStatic
            fun main(args: Array<String>) {
                val epochs = 100
                val rule = GenericRules("4555",4,5,5,5)
                val boardName = "glider3D_4555"
                val inputFileName = "golBoards/"+ boardName
                val outputFileName = boardName + "_"+ rule.name+"_" + epochs + "_"

//                val inputFileName = "golBoards/exploder"
//                val outputFileName = "dense"

                val boundZ = true
//            var oldBoard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)
//            var newboard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)

                val simulations = 0
                val metricsList = mutableListOf<GOLMetrics>()
                for (i in 0..simulations) {
//                    val board = GOLRandomBoard.generate(100, 100, 1, 5, 5, 0, 0.5)
                    val board = GOLBoardReader.generate(inputFileName)
                    metricsList.add(run(board, epochs, rule, true))
                }

                val aggregated = GOLAggregatedMetrics.getAggregatedMetrics(metricsList)
                aggregated.particles.forEach {
                    println(it)
                }

            }
    }
}

