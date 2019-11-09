package matrices.mult;

import matrices.Cuadrante;
import matrices.IMatrix;

public class ThreadedMultiplication implements IMultiplication {
	@Override
	public IMatrix multiply(IMatrix a, IMatrix b) {
		if (a.getColumns() != b.getRows()) {
			return null;
		}
		IMatrix r = a.createMatrix(a.getRows(), b.getColumns());
		ResultadoConcurrente resultadosConcurrentes[] = {
				new ResultadoConcurrente(a, b, r, Cuadrante.primero),
				new ResultadoConcurrente(a,b,r,Cuadrante.segundo),
				new ResultadoConcurrente(a,b,r,Cuadrante.tercero),
				new ResultadoConcurrente(a,b,r,Cuadrante.cuarto)
		};
		for(ResultadoConcurrente resultadoConcurrente : resultadosConcurrentes){
			resultadoConcurrente.start();
		}
		for(ResultadoConcurrente resultadoConcurrente: resultadosConcurrentes){
			try {
				resultadoConcurrente.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return r;
	}
}
