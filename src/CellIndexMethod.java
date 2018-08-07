import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class CellIndexMethod {

    final static double EPSILON = 0.0000001;

    private void cellIndexMethod(final ArrayList<Particula> particulas, final int n, final double l, final int m, final double rc, final boolean borderless) {
        ArrayList<Particula>[][] matriz = new ArrayList[m][m];
        for(int i=0; i<m; i++){
            for (int j=0; j<m; j++){
                matriz[i][j] = new ArrayList<>();
            }
        }

        double maxR = 0;
        for (Particula p: particulas) {

            maxR = Math.max(maxR, p.r);

            int x = (int) Math.floor(p.x*m/l);  //Ejemplo, si l=7 y m=2, quiero que si x<3.5 sea 0 sino 1.
            if(x == m) x = m-1; //Si esta en el extremo derecho, l=7.0 p.x=7.0 m=7 => 7.0*7/7.0 = 7, pero 7>=m.
            if(x == -1) x = 0; //Problemas de punto flotante te puede quedar p.x*m/l = -0.00000001? Tal vez? No creo. Nunca se sabe.

            int y = (int) Math.floor(p.y*m/l);
            if(y == m) y-=1;
            if(y == -1) y=0; //Problemas de punto flotante te puede quedar p.x*m/l = -0.00000001? Tal vez? No creo. Nunca se sabe.

            matriz[x][y].add(p);
        }
        /*
        Solo vamos a mirar las celdas vecinas. Asumir peor caso.
        La particula está en una esquina y la otra particula tiene el borde en una celda pero el centro en otra.
        Para puntos sin randios: Se necesita tener L/M > Rc para que todos los vecinos esten en celdas vecinas
        Si tienen radios: L/M > Rc + R1 + R2 => L/M > Rc + 2*MaxRadio
         */
        if(l/m <= rc + 2* maxR - EPSILON){
            throw new IllegalArgumentException("l/m <= rc + 2 * maxR"); //TODO no tirar exception sino que avisar o algo?
        }

        calcularVecinos(matriz, n, l, m, rc, borderless);

        imprimirVecinos("vecinos.txt", particulas);

    }

    private void imprimirVecinos(String fileName, ArrayList<Particula> particulas) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            for (Particula p: particulas) {
                writer.write(p.id + "\t");
                for (Particula vecino: p.vecinos){
                    writer.write(vecino.id + "\t");
                }
                writer.write("\n");
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            //TODO do something with error
        }
    }

    private void calcularVecinos(final ArrayList<Particula>[][] matriz, final int n, final double L, final int m, final double rc, final boolean borderless) {
        int[][] direcciones = new int[][]{{0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int i = 0; i < m; i++){
            for(int j=0; j < m; j++){
                ArrayList<Particula> celda = matriz[i][j];

                // Calcular vecinos dentro de la celda
                for(int index1=0; index1<celda.size(); index1++){
                    for(int index2=index1+1; index2<celda.size(); index2++){
                        Particula p = celda.get(index1);
                        Particula pVecina = celda.get(index2);
                        if(p.distanciaEuclidianaBordeABorde(pVecina, false, false, L) <= rc){
                            p.vecinos.add(pVecina);
                            pVecina.vecinos.add(p);
                        }
                    }
                }
                if(m>1){
                    //Calcular vecinos en las celdas vecinas
                    for (Particula p: celda) {
                        for(int[] direccion: direcciones){
                            int xVecino = i + direccion[0];
                            int yVecino = j + direccion[1];
                            boolean borderXSwap = false;
                            boolean borderYSwap = false;
                            if (borderless){
                                if(xVecino < 0){
                                    xVecino = m-1;
                                    borderXSwap = true;
                                }
                                if(xVecino >= m){
                                    xVecino = 0;
                                    borderXSwap = true;
                                }
                                if(yVecino < 0){
                                    borderYSwap = true;
                                    yVecino = m-1;
                                }
                                if(yVecino >= m){
                                    borderYSwap = true;
                                    yVecino = 0;
                                }
                            }
                            if (xVecino >= 0 && xVecino < m && yVecino >= 0 && yVecino < m) {
                                ArrayList<Particula> celdaVecina = matriz[xVecino][yVecino];
                                for (Particula pVecina : celdaVecina) {
//                                if (p != pVecina) {
                                    if(p.distanciaEuclidianaBordeABorde(pVecina, borderXSwap, borderYSwap, L) <= rc){
                                        p.vecinos.add(pVecina);
                                        pVecina.vecinos.add(p);
                                    }
//                                }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void main(String [] args){
//        CellIndexMethod CIM = new CellIndexMethod();
//
//        ArrayList<Particula> particulas = new ArrayList<Particula>();
//        particulas.add(new Particula(0,1.0,2.0,0.2));
//        particulas.add(new Particula(1,1.5,2.0,0.2));
//        particulas.add(new Particula(2,2.0,2.0,0.2));
//        particulas.add(new Particula(3,3.0,2.0,0.5));
//        particulas.add(new Particula(4,1.0,2.5,0.2));
//        particulas.add(new Particula(5,0.1,2.5,0.2));
//        particulas.add(new Particula(6,9.9,2.5,0.2));
//        int n = 5;
//        double l = 10.0;
//        int m = 5;
//        double rc = 0.5;
//        CIM.cellIndexMethod(particulas, n, l, m, rc, false);
//        for(int i=0; i<particulas.size(); i++){
//            imprimirColoreo(particulas, i, "colores.txt");
//        }

        calcularTiempos();

    }

    private static void calcularTiempos() {
        CellIndexMethod CIM = new CellIndexMethod();
        int n = 500;
        double maxR = 0.5;
        double l = 100.0;
        double iteraciones = 1000;
        double rc = 1.0;
        boolean borderless = true;
        int maxM = 50;
        double[] timesM = new double[maxM];
        for(int i=0; i<iteraciones; i++){
            if(i%5 == 0) System.out.println("Iteracion N° " + i);
            ArrayList<Particula> particulas = generarParticulas(n, maxR, l);
            for(int m=1; m<=maxM; m++){
                double start = System.nanoTime();
                CIM.cellIndexMethod(particulas, n, l, m, rc, borderless);
                double time =  System.nanoTime() - start;
                timesM[m-1] = (timesM[m-1]*i+time)/(i+1);
            }
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("tiempo_CIM.csv"), "utf-8"))) {
            for(int m=1; m<=maxM; m++){
                writer.write( m+ "," + timesM[m-1]/1000000+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
            //TODO something with exception
        }
    }

    private static ArrayList<Particula> generarParticulas(int n, double maxR, double l) {
        ArrayList<Particula> particulas = new ArrayList<>();
        Random generator = new Random();
        for(int i=0; i<n; i++){
            particulas.add(new Particula(i, generator.nextDouble()*l, generator.nextDouble()*l, generator.nextDouble()*maxR));
        }
        return particulas;
    }

    final static int PARTICULA_ELEGIDA_R = 200;
    final static int PARTICULA_ELEGIDA_G = 0;
    final static int PARTICULA_ELEGIDA_B = 0;
    final static int PARTICULA_VECINA_R = 0;
    final static int PARTICULA_VECINA_G = 200;
    final static int PARTICULA_VECINA_B = 0;
    final static int PARTICULA_OTRA_R = 0;
    final static int PARTICULA_OTRA_G = 0;
    final static int PARTICULA_OTRA_B = 200;


    private static void imprimirColoreo(ArrayList<Particula> particulas, int indexParticula, String fileName) {
        int[] coloresParticulaElegida = new int[]{PARTICULA_ELEGIDA_R, PARTICULA_ELEGIDA_G, PARTICULA_ELEGIDA_B};
        int[] coloresParticulaVecina = new int[]{PARTICULA_VECINA_R, PARTICULA_VECINA_G, PARTICULA_VECINA_B};
        int[] coloresParticulaOtra = new int[]{PARTICULA_OTRA_R, PARTICULA_OTRA_G, PARTICULA_OTRA_B};

        Particula pElegida = particulas.get(indexParticula);


        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(indexParticula + "_" + fileName), "utf-8"))) {
            writer.write(particulas.size() + "\n");
            writer.write("\n");
            for (Particula p: particulas) {
                writer.write(p.id + "\t");
                writer.write(p.x + "\t");
                writer.write(p.y + "\t");
                writer.write(0 + "\t");
                writer.write(p.r + "\t");
                if(p.equals(pElegida)){
                    imprimirColores(writer, coloresParticulaElegida);
                }else if(pElegida.vecinos.contains(p)){  //TODO hacer orden 1 en vez de N. Usar Set para vecinos? Tal vez ya es O(1), leer implementacion de ArrayList
                    imprimirColores(writer, coloresParticulaVecina);
                }else{
                    imprimirColores(writer, coloresParticulaOtra);
                }
                writer.write("\n");
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            //TODO do something with error
        }



    }

    private static void imprimirColores(Writer writer, int[] coloresRGB) throws IOException {
        writer.write(coloresRGB[0] + "\t");
        writer.write(coloresRGB[1] + "\t");
        writer.write(coloresRGB[2] + "\t");
    }


    private static class Particula{

        ArrayList<Particula> vecinos;

        int id; // TODO Diego: Se podria hacer que se pongan solos los ID. Se lo deje al usuario por ahora, no se que es mejor.
        double x;
        double y;
        double r;

        public Particula(int id, double x, double y, double r) {
            vecinos = new ArrayList<>();
            this.id = id;
            this.x = x;
            this.y = y;
            this.r = r;
        }

        public double distanciaEuclidianaBordeABorde(Particula pVecina, boolean swapX, boolean swapY, double L) {
            double diffX = pVecina.x - x;
            if(swapX) diffX = L - Math.abs(diffX);
            double diffY = pVecina.y - y;
            if(swapY) diffY = L - Math.abs(diffY);
            return Math.sqrt(diffX*diffX + diffY*diffY) - r - pVecina.r;
        }
    }
}
