package matrices;

import matrices.mult.SimpleMultiplication;
import matrices.mult.SyncMultiplication;
import matrices.mult.ThreadedMultiplication;

import javax.sound.midi.MidiSystem;

public class MainMatrices {
	private static final int n = 1200;
	//    private static final int n = 200;
	public static void main(String[] args) {
		DenseMatrix m1 = (DenseMatrix) Utils.generateDenseSquareMatrix(n);
		DenseMatrix m2 = (DenseMatrix) Utils.generateDenseSquareMatrix(n);

//		SparseMatrix m1 = (SparseMatrix)Utils.generateSparseSquareMatrix(n, .9);
//		SparseMatrix m2 = (SparseMatrix)Utils.generateSparseSquareMatrix(n, .9);

        /*Version Secuencial
        TIEMPO DE EJECUCION EN PROMEDIO: 30784.0 */
		SimpleMultiplication simple = new SimpleMultiplication();
		SyncMultiplication multiplication = new SyncMultiplication();
      /*  long sumaTh = 0;
        long sumaS = 0;
        for (int i = 0; i < 10; i++) {
            long timeTh = Utils.measureTime(m1, m2, multiplication);
            long timeS = Utils.measureTime(m1,m2,simple);
            sumaTh+= timeTh;
            sumaS += timeS;
        }
        double tiempoS = sumaS /10;*/
        /*long tiempoTh  = Utils.measureTime(m1, m2, multiplication);
        System.out.println("Promedio: "+tiempoTh);
*/
        //        System.out.println("Promedio: "+tiempoS);)
		System.out.println("Tiempo multiplicacion simple: "+Utils.measureTime(m1, m2, simple));
		int x = 3;
        double tiempoTh = System.currentTimeMillis();
		Utils.verifyMultiplication(m1,m2,multiplication, x);
		System.out.println("Tiempo promedio: "+(System.currentTimeMillis() - tiempoTh)/x);
	}
}
