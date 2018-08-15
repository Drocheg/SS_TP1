package jeuDeLaVie

class GOLMetrics (val particles: MutableList<Int> = mutableListOf<Int>(),
                  val centersOfMass: MutableList<Coordinate?> = mutableListOf<Coordinate?>(),
                  val momentsOfInertia: MutableList<Double> = mutableListOf<Double>()) {

    fun feed(board: GOLBoard) {
        particles.add(board.count);
        centersOfMass.add(calculateCenterOfMass(board));
        momentsOfInertia.add(calculateMoment(board));
    }

    private fun calculateCenterOfMass(board: GOLBoard) : Coordinate? {
        var xm : Int = 0;
        var ym: Int = 0;
        var zm : Int = 0;

        for(i in 0..board.x)
            for(j in 0..board.y)
                for(k in 0..board.x) {
                    val particle = board[i,j,k];
                    xm += particle * i;
                    ym += particle * j;
                    zm += particle * k;
                }

        val n : Int = board.count;
        return if (n == 0) null else Coordinate(xm/n, ym/n, zm/n);
    }

    private fun calculateMoment(board: GOLBoard) : Double {
        var moment: Double = 0.0;
        val centerX: Double = board.x / 2.0;
        val centerY: Double = board.y / 2.0;

        for(i in 0..board.x)
            for(j in 0..board.y)
                for(k in 0..board.x) {
                    val particle = board[i,j,k];
                    if(particle > 0) {
                        moment += Math.pow(centerX - i, 2.0) + Math.pow(centerY - j, 2.0);
                    }
                }

        return moment;
    }
}