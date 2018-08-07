import java.*
import java.io.*
import java.util.ArrayList
import java.util.Random

class CellIndexMethod {

    private fun cellIndexMethod(particulas: ArrayList<Particula>, n: Int, l: Double, m: Int, rc: Double, borderless: Boolean) {
        val matriz = Array(m) { Array(m) { ArrayList<Particula>() }}

        var maxR = 0.0
        for (p in particulas) {

            maxR = Math.max(maxR, p.r)

            var x = Math.floor(p.x * m / l).toInt()  //Ejemplo, si l=7 y m=2, quiero que si x<3.5 sea 0 sino 1.
            if (x == m) x = m - 1 //Si esta en el extremo derecho, l=7.0 p.x=7.0 m=7 => 7.0*7/7.0 = 7, pero 7>=m.
            if (x == -1) x = 0 //Problemas de punto flotante te puede quedar p.x*m/l = -0.00000001? Tal vez? No creo. Nunca se sabe.

            var y = Math.floor(p.y * m / l).toInt()
            if (y == m) y -= 1
            if (y == -1) y = 0 //Problemas de punto flotante te puede quedar p.x*m/l = -0.00000001? Tal vez? No creo. Nunca se sabe.

            matriz[x][y].add(p)
        }
        /*
        Solo vamos a mirar las celdas vecinas. Asumir peor caso.
        La particula está en una esquina y la otra particula tiene el borde en una celda pero el centro en otra.
        Para puntos sin randios: Se necesita tener L/M > Rc para que todos los vecinos esten en celdas vecinas
        Si tienen radios: L/M > Rc + R1 + R2 => L/M > Rc + 2*MaxRadio
         */
        if (l / m <= rc + 2 * maxR - EPSILON) {
            throw IllegalArgumentException("l/m <= rc + 2 * maxR") //TODO no tirar exception sino que avisar o algo?
        }

        calcularVecinos(matriz, n, l, m, rc, borderless)

        imprimirVecinos("textOutput/vecinos.txt", particulas)

    }

    private fun imprimirVecinos(fileName: String, particulas: ArrayList<Particula>) {
        try {
            BufferedWriter(OutputStreamWriter(
                    FileOutputStream(fileName), "utf-8")).use { writer ->
                for (p in particulas) {
                    writer.write(p.id.toString() + "\t")
                    for (vecino in p.vecinos) {
                        writer.write(vecino.id.toString() + "\t")
                    }
                    writer.write("\n")
                }
                writer.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //TODO do something with error
        }

    }

    private fun calcularVecinos(matriz: Array<Array<ArrayList<Particula>>>, n: Int, L: Double, m: Int, rc: Double, borderless: Boolean) {
        val direcciones = arrayOf(intArrayOf(0, 1), intArrayOf(1, -1), intArrayOf(1, 0), intArrayOf(1, 1))
        for (i in 0 until m) {
            for (j in 0 until m) {
                val celda = matriz[i][j]

                // Calcular vecinos dentro de la celda
                for (index1 in celda.indices) {
                    for (index2 in index1 + 1 until celda.size) {
                        val p = celda[index1]
                        val pVecina = celda[index2]
                        if (p.distanciaEuclidianaBordeABorde(pVecina, false, false, L) <= rc) {
                            p.vecinos.add(pVecina)
                            pVecina.vecinos.add(p)
                        }
                    }
                }
                if (m > 1) {
                    //Calcular vecinos en las celdas vecinas
                    for (p in celda) {
                        for (direccion in direcciones) {
                            var xVecino = i + direccion[0]
                            var yVecino = j + direccion[1]
                            var borderXSwap = false
                            var borderYSwap = false
                            if (borderless) {
                                if (xVecino < 0) {
                                    xVecino = m - 1
                                    borderXSwap = true
                                }
                                if (xVecino >= m) {
                                    xVecino = 0
                                    borderXSwap = true
                                }
                                if (yVecino < 0) {
                                    borderYSwap = true
                                    yVecino = m - 1
                                }
                                if (yVecino >= m) {
                                    borderYSwap = true
                                    yVecino = 0
                                }
                            }
                            if (xVecino >= 0 && xVecino < m && yVecino >= 0 && yVecino < m) {
                                val celdaVecina = matriz[xVecino][yVecino]
                                for (pVecina in celdaVecina) {
                                    if (p.distanciaEuclidianaBordeABorde(pVecina, borderXSwap, borderYSwap, L) <= rc) {
                                        p.vecinos.add(pVecina)
                                        pVecina.vecinos.add(p)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private class Particula(internal var id: Int // TODO Diego: Se podria hacer que se pongan solos los ID. Se lo deje al usuario por ahora, no se que es mejor.
                            , internal var x: Double, internal var y: Double, internal var r: Double) {

        internal var vecinos: HashSet<Particula>

        init {
            vecinos = HashSet()
        }

        fun distanciaEuclidianaBordeABorde(pVecina: Particula, swapX: Boolean, swapY: Boolean, L: Double): Double {
            var diffX = pVecina.x - x
            if (swapX) diffX = L - Math.abs(diffX)
            var diffY = pVecina.y - y
            if (swapY) diffY = L - Math.abs(diffY)
            return Math.sqrt(diffX * diffX + diffY * diffY) - r - pVecina.r
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Particula

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            return id
        }

    }

    companion object {

        internal val EPSILON = 0.0000001


        @JvmStatic
        fun main(args: Array<String>) {
            //        CellIndexMethod CIM = new CellIndexMethod();
            //
            val n = 200
            val l = 10.0
            val m = 8
            val rc = 0.5
            val particulas = generarParticulas(n, 0.2, l)
            CellIndexMethod().cellIndexMethod(particulas, n, l, m, rc, false)

            for(i in 0 until particulas.size) {
                imprimirColoreo(particulas, i, "colores.txt")
            }
        }

        private fun calcularTiempos() {
            val CIM = CellIndexMethod()
            val n = 500
            val maxR = 0.5
            val l = 100.0
            val iteraciones = 1000.0
            val rc = 1.0
            val borderless = true
            val maxM = 50
            val timesM = DoubleArray(maxM)
            var i = 0
            while (i < iteraciones) {
                if (i % 5 == 0) println("Iteracion N° " + i)
                val particulas = generarParticulas(n, maxR, l)
                for (m in 1..maxM) {
                    val start = System.nanoTime().toDouble()
                    CIM.cellIndexMethod(particulas, n, l, m, rc, borderless)
                    val time = System.nanoTime() - start
                    timesM[m - 1] = (timesM[m - 1] * i + time) / (i + 1)
                }
                i++
            }
            try {
                BufferedWriter(OutputStreamWriter(
                        FileOutputStream("textOutput/tiempo_CIM.csv"), "utf-8")).use { writer ->
                    for (m in 1..maxM) {
                        writer.write(m.toString() + "," + timesM[m - 1] / 1000000 + "\n")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //TODO something with exception
            }

        }

        private fun generarParticulas(n: Int, maxR: Double, l: Double): ArrayList<Particula> {
            val particulas = ArrayList<Particula>()
            val generator = Random()
            for (i in 0 until n) {
                particulas.add(Particula(i, generator.nextDouble() * l, generator.nextDouble() * l, generator.nextDouble() * maxR))
            }
            return particulas
        }

        internal val PARTICULA_ELEGIDA_R = 200
        internal val PARTICULA_ELEGIDA_G = 0
        internal val PARTICULA_ELEGIDA_B = 0
        internal val PARTICULA_VECINA_R = 0
        internal val PARTICULA_VECINA_G = 200
        internal val PARTICULA_VECINA_B = 0
        internal val PARTICULA_OTRA_R = 0
        internal val PARTICULA_OTRA_G = 0
        internal val PARTICULA_OTRA_B = 200


        private fun imprimirColoreo(particulas: ArrayList<Particula>, indexParticula: Int, fileName: String) {
            val coloresParticulaElegida = intArrayOf(PARTICULA_ELEGIDA_R, PARTICULA_ELEGIDA_G, PARTICULA_ELEGIDA_B)
            val coloresParticulaVecina = intArrayOf(PARTICULA_VECINA_R, PARTICULA_VECINA_G, PARTICULA_VECINA_B)
            val coloresParticulaOtra = intArrayOf(PARTICULA_OTRA_R, PARTICULA_OTRA_G, PARTICULA_OTRA_B)

            val pElegida = particulas[indexParticula]


            try {
                BufferedWriter(OutputStreamWriter(
                        FileOutputStream(indexParticula.toString() + "_" + fileName), "utf-8")).use { writer ->
                    writer.write(particulas.size.toString() + "\n")
                    writer.write("\n")
                    for (p in particulas) {
                        writer.write(p.id.toString() + "\t")
                        writer.write(p.x.toString() + "\t")
                        writer.write(p.y.toString() + "\t")
                        writer.write(0.toString() + "\t")
                        writer.write(p.r.toString() + "\t")
                        if (p == pElegida) {
                            imprimirColores(writer, coloresParticulaElegida)
                        } else if (pElegida.vecinos.contains(p)) {  //TODO hacer orden 1 en vez de N. Usar Set para vecinos? Tal vez ya es O(1), leer implementacion de ArrayList
                            imprimirColores(writer, coloresParticulaVecina)
                        } else {
                            imprimirColores(writer, coloresParticulaOtra)
                        }
                        writer.write("\n")
                    }
                    writer.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                //TODO do something with error
            }


        }

        @Throws(IOException::class)
        private fun imprimirColores(writer: Writer, coloresRGB: IntArray) {
            writer.write(coloresRGB[0].toString() + "\t")
            writer.write(coloresRGB[1].toString() + "\t")
            writer.write(coloresRGB[2].toString() + "\t")
        }
    }
}
