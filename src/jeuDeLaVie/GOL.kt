package jeuDeLaVie

class GOL {

    companion object {

        fun run(initial: GOLBoard, epochs: Int, rule: GOLRule, save: Boolean = false, filename: String = "out") : GOLMetrics {
            var oldBoard = initial;
            var newboard = GOLBoard(oldBoard);

            val metrics = GOLMetrics();
            if (save) newboard.ovitoBW(0, filename)
            for (e in 1 .. epochs) {
                metrics.feed(oldBoard);
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

                if (save) newboard.ovitoBW(e, filename)
                newboard.ovitoDistance(e, Triple(0, 0, 0))
            }
            return metrics;
        }

            @JvmStatic
            fun main(args: Array<String>) {
                val epochs = 20
                val rule = GenericRules(3, 3, 2, 3)
//            val x = 200
//            val y = 200
//            val z = 200
                //var oldBoard = GOLRandomBoard.generate(x,y,z)
                //var newboard = GOLRandomBoard.generate(x,y,z)
                val inputFileName = "golBoards/exploder"
                val outputFileName = "dense"
                val boundZ = true
//            var oldBoard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)
//            var newboard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)

                val simulations = 20;
                val metricsList = mutableListOf<GOLMetrics>();

                for (i in 0..simulations) {
                    val board = GOLRandomBoard.generate(100, 100, 1, 5, 5, 0, 0.5);
                    metricsList.add(run(board, epochs, rule));
                }

                val aggregated = GOLAggregatedMetrics.getAggregatedMetrics(metricsList);
                aggregated.particles.forEach {
                    println(it);
                }

            }
    }
}