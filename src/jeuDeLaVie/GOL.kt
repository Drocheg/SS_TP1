package jeuDeLaVie

import java.lang.System.exit
import java.util.*
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
            var newboard = initial.clone()

            val metrics = GOLMetrics()
            metrics.feed(newboard)
            if (save) {
                val centerMass = metrics.centersOfMass.last()
                newboard.ovitoSave(0,centerMass, maxDistance)
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
                    newboard.ovitoSave(e,centerMass, maxDistance)
                }
            }
            return metrics
        }

            @JvmStatic
            fun main(args: Array<String>) {
                val epochs = 100
                val rule = GenericRules(4,5,5,5)
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
                    val rand = Random(120)
                    val board = GOLRandomBoard.generate(100, 100, 100, 10, 10, 10, 0.5, seed = rand.nextLong())
//                    val board = GOLBoardReader.generate(inputFileName)
                    metricsList.add(run(board, epochs, rule, true))
                }

                val aggregated = GOLAggregatedMetrics.getAggregatedMetrics(metricsList)
                aggregated.particles.forEach {
                    println(it)
                }

            }
    }
}

