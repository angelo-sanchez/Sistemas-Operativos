package matrices.mult;

import matrices.Cuadrante;
import matrices.IMatrix;

public class SyncMultiplication implements IMultiplication {
	@Override
	public IMatrix multiply(IMatrix a, IMatrix b) {
		if(a.getColumns() == b.getRows()){
			IMatrix result = a.createMatrix(a.getRows(),b.getColumns());
			ResultadoConcurrenteSync resultadosConcurrentes[] = {
				new ResultadoConcurrenteSync(a, b, result, Cuadrante.primero),
				new ResultadoConcurrenteSync(a,b,result,Cuadrante.segundo),
				new ResultadoConcurrenteSync(a,b,result,Cuadrante.tercero),
				new ResultadoConcurrenteSync(a,b,result,Cuadrante.cuarto)
			};
			for (ResultadoConcurrenteSync rs : resultadosConcurrentes) {
				rs.start();
			}
			for (ResultadoConcurrenteSync rs : resultadosConcurrentes) {
				try {
					rs.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		return null;
	}
}
