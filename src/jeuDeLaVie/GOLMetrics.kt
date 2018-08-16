package jeuDeLaVie

class GOLMetrics (val particles: MutableList<Int> = mutableListOf<Int>(),
                  val centersOfMass: MutableList<Coordinate?> = mutableListOf<Coordinate?>(),
                  val momentsOfInertia: MutableList<Double> = mutableListOf<Double>(),
                  val reachs: MutableList<Double> = mutableListOf<Double>(),
                  var epochs : Int = 0) {

    fun feed(board: GOLBoard) {
        particles.add(board.count)

        val centerOfMass = calculateCenterOfMass(board)
        centersOfMass.add(centerOfMass)
        reachs.add(maximumDistanceToCenter(board, centerOfMass))
        momentsOfInertia.add(calculateMoment(board))
        epochs++
    }

    private fun maximumDistanceToCenter(board: GOLBoard, centerOfMass: Coordinate?): Double {
        if(centerOfMass == null) return 0.0

        val c : Coordinate = centerOfMass

        var maxDistance = 0.0

        for (i in 0 until board.x)
            for (j in 0 until board.y)
                for(k in 0 until board.z) {
                    if(board[i,j,k] > 0) {
                        val distance = Math.sqrt(
                                ((c.x - i) * (c.x - i) +
                                        (c.y - j) * (c.y - j) +
                                        (c.z - k) * (c.z - k))
                        )

                        if (distance > maxDistance) {
                            maxDistance = distance
                        }
                    }

                }
        return maxDistance
    }


    private fun calculateCenterOfMass(board: GOLBoard) : Coordinate? {
        var xm = 0.0
        var ym = 0.0
        var zm = 0.0

        for(i in 0 until board.x)
            for(j in 0 until board.y)
                for(k in 0 until board.x) {
                    val particle = board[i,j,k]
                    xm += particle * i
                    ym += particle * j
                    zm += particle * k
                }

        val n : Int = board.count
        return if (n == 0) null else Coordinate(xm/n, ym/n, zm/n)
    }

    private fun calculateMoment(board: GOLBoard) : Double {
        var moment: Double = 0.0
        val centerX: Double = board.x / 2.0
        val centerY: Double = board.y / 2.0

        for(i in 0 until board.x)
            for(j in 0 until board.y)
                for(k in 0 until board.x) {
                    val particle = board[i,j,k]
                    if(particle > 0) {
                        moment += Math.pow(centerX - i, 2.0) + Math.pow(centerY - j, 2.0)
                    }
                }

        return moment
    }
}