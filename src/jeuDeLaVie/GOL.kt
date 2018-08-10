package jeuDeLaVie

class GOL {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val epochs = 100
            val rule = GenericRules()
            val x = 100
            val y = 100
            val z = 100

            var oldBoard = GOLRandomBoard.generate(x,y,z)
            var newboard = GOLRandomBoard.generate(x,y,z)

            for (e in 0 until epochs) {
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