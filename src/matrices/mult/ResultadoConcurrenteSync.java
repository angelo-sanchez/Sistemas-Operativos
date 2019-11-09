package matrices.mult;

import matrices.Cuadrante;
import matrices.IMatrix;

;

public class ResultadoConcurrenteSync extends Thread {
    private IMatrix a, b, result;
    private int x0, x1, y0, y1;
    public ResultadoConcurrenteSync(IMatrix a, IMatrix b, IMatrix r, Cuadrante cuadrante ) {
        this.a = a;
        this.b = b;
        this.result = r;
        if(cuadrante == Cuadrante.primero || cuadrante == Cuadrante.tercero){
            x0 = 0; x1 = r.getColumns()/2;
        } else if(cuadrante == Cuadrante.segundo || cuadrante == Cuadrante.cuarto){
            x0 = r.getColumns()/2; x1 = r.getColumns();
        }
        if(cuadrante == Cuadrante.primero || cuadrante == Cuadrante.segundo){
            y0 = 0; y1 = r.getRows()/2;
        }else if(cuadrante == Cuadrante.tercero || cuadrante == Cuadrante.cuarto){
            y0 = r.getRows()/2; y1 = r.getRows();
        }
    }

    @Override
    public void run() {
        for(int i = y0; i<y1; i++){
            for (int j = x0; j < x1; j++) {
                double r = 0;
                for(int k = 0; k < a.getColumns(); k++ ){
                    r += (a.get(i,k)*b.get(k,j));
                }
                synchronized (result){
                    result.set(i, j, r);
                }
            }
        }
    }
}
