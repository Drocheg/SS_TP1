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
            val inputFileName = "golBoards/3D_test1"
            val outputFileName = "3D_test1_50_"
            val boundZ = true
            var oldBoard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)
            var newboard = GOLBoardReader.generate(inputFileName, boundZ = boundZ)
            newboard.ovitoBW(0, outputFileName)
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

                newboard.ovitoBW(e, outputFileName)
                newboard.ovitoDistance(e, Triple(0,0,0))
            }
        }
    }
}