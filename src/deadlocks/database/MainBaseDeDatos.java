package deadlocks.database;

public class MainBaseDeDatos {
	public static void main(String[] args) {
		int m = 10;
		ProcesoEscritor[] escritores = new ProcesoEscritor[m];
		BaseDeDatos db = new BaseDeDatos(m);
		for (int i = 0; i < m; i++) {
			try {
				db.escribir(i, "Agregado por defecto");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int n = 50;
		ProcesoLector[] lectores = new ProcesoLector[n];
		for(int i = 0; i < m; i++){
			escritores[i] = new ProcesoEscritor(db);
			escritores[i].start();
		}
		for (int i = 0; i < lectores.length; i++) {
			lectores[i] = new ProcesoLector(db);
			lectores[i].start();
		}
		for(int i = 0; i < n+m; i++){
			try {
				if(i < n )
					lectores[i].join();
				else {
					escritores[i-n].join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
