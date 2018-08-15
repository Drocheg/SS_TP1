package jeuDeLaVie

class GOLAggregatedMetrics (val particles: List<Pair<Double, Double>> = mutableListOf<Pair<Double, Double>>(),
                            val momentsOfInertia: List<Pair<Double, Double>> = mutableListOf<Pair<Double, Double>>()) {

    companion object {

        fun getStats(list: List<Number>) : Pair<Double, Double> {
            val size = list.size;
            var sum = 0.0;
            var stdAcum = 0.0;

            for (num in list) {
                sum += num.toDouble();
            }

            val mean : Double = sum/size;

            for (num in list) {
                stdAcum += (num.toDouble() - mean) * (num.toDouble() - mean);
            }

            val std = Math.sqrt(stdAcum / (size - 1));

            return Pair(mean, std);
        }

        fun getAggregatedMetrics(metricsList : List<GOLMetrics>) : GOLAggregatedMetrics {
            if(metricsList.isEmpty()) return GOLAggregatedMetrics();

            val size = metricsList[0].epochs;
            println(size);

            for (m in metricsList) {
                if (m.epochs != size) throw IllegalArgumentException();
            }

            val aggregatedParticles = mutableListOf<Pair<Double, Double>>();
            val aggregatedMomentsOfInertia = mutableListOf<Pair<Double, Double>>();


            for(i in 0 until size) {
                val particles = mutableListOf<Int>();
                val momentsOfInertia = mutableListOf<Double>();
                for (m in metricsList) {
                    particles.add(m.particles[i]);
                    momentsOfInertia.add(m.momentsOfInertia[i]);
                }

                aggregatedParticles.add(getStats(particles));
                aggregatedMomentsOfInertia.add(getStats(momentsOfInertia));
            }

            return GOLAggregatedMetrics(aggregatedParticles, aggregatedMomentsOfInertia);
        }
    }

}