package jeuDeLaVie

class GOL {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val epochs = 10
            val rule = StandarRules()
            val x = 200
            val y = 200
            val z = 200

            var oldBoard = GOLRandomBoard.generate(x,y,z)
            var newboard = GOLRandomBoard.generate(x,y,z)
            newboard.outputOvito("ovito/gol", 0)
            for (e in 1 until epochs) {
                println(e)

                val aux = oldBoard
                oldBoard = newboard
                newboard = aux

                for(i in 0 until x) {
                    for(j in 0 until y) {
                        for(k in 0 until z) {
                            rule.modify(oldBoard, newboard, i,j,k)
                        }
                    }
                }

                newboard.outputOvito("ovito/gol", e)
            }
        }
    }
}