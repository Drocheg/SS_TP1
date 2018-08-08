package jeuDeLaVie

class GOL {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val epochs = 10
            val rule = StandarRules()
            val x = 10
            val y = 3
            val z = 1

            var oldBoard = GOLRandomBoard.generate(x,y,z)
            var newboard = GOLRandomBoard.generate(x,y,z)

            for (e in 0 until epochs) {
                val aux = oldBoard
                oldBoard = newboard
                newboard = aux

                print(oldBoard)

                for(i in 0 until x) {
                    for(j in 0 until y) {
                        for(k in 0 until z) {
                            rule.modify(oldBoard, newboard, i,j,k)
                        }
                    }
                }

                println(newboard)
            }
        }
    }
}