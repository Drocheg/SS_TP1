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
                maxDistance: Int = 20) : GOLMetrics {
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

        fun runAging(
                initial: GOLBoard,
                epochs: Int,
                rule: AgingRule,
                save: Boolean = false) : GOLMetrics {
            var oldBoard = initial
            var newboard = initial.clone()

            val metrics = GOLMetrics()
            metrics.feed(newboard)
            if (save) {
                rule.printRule(0, newboard)
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
                    rule.printRule(e, newboard)
                }
            }
            return metrics
        }

            @JvmStatic
            fun main(args: Array<String>) {
                val epochs = 100
//                val rule = GenericRules(2,3,3,3) //2DS
//                val rule = GenericRules(2,3,2,4) //2DE
//                val rule = GenericRules(3,5,4,4) //2DD
//                val rule = GenericRules(9,9,3,3) //2DMG
//                val rule = GenericRules(4,6,6,7) //3DS
//                val rule = GenericRules(2,3,3,3) //3DE
//                val rule = GenericRules(4,6,7,9) //3DD
//                val rule = GenericRules(30,30,8,8) //3DMG
//                val rule = GenericRules(4,5,5,5) //3DMG
                val boardName = "oscillator"
                val inputFileName = "golBoards/"+ boardName

                val simulations = 20
                val metricsList = mutableListOf<GOLMetrics>()
                val rand = Random(120)
                for (i in 0 until simulations) {
//                    val board = GOLRandomBoard.generate(40, 40, 40, 5, 5, 5, 0.2, seed = rand.nextLong())
//                    val board = GOLRandomBoard.generate(80, 80, 1, 8, 8, 1, 0.2, seed = rand.nextLong(), boundZ = true)
//                    val board = GOLBoardReader.generate(inputFileName, boundZ = true)
//                    metricsList.add(run(board, epochs, rule, i == 0))
//                    metricsList.add(runAging(board, epochs, AgingRule(rule, 1), i == 0))
                }

                val aggregated = GOLAggregatedMetrics.getAggregatedMetrics(metricsList)
                aggregated.output("output")
            }
    }
}

