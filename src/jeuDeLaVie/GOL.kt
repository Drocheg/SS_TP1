package jeuDeLaVie

class GOL {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val epochs = 50
            val rule = StandarRules()
//            val x = 200
//            val y = 200
//            val z = 200
            //var oldBoard = GOLRandomBoard.generate(x,y,z)
            //var newboard = GOLRandomBoard.generate(x,y,z)
            val inputFileName = "golBoards/glider"
            val outputFileName = "ovito/gol_glider_50_"
            val boundZ = true
            var oldBoard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)
            var newboard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)
            newboard.outputOvito(outputFileName, 0)
            for (e in 1 until epochs) {
                println(e)

                val aux = oldBoard
                oldBoard = newboard
                newboard = aux

                for(i in 0 until oldBoard.x) {
                    for(j in 0 until oldBoard.y) {
                        for(k in 0 until oldBoard.z) {
                            rule.modify(oldBoard, newboard, i,j,k)
                        }
                    }
                }

                newboard.outputOvito(outputFileName, e)
            }
        }
    }
}