package deadlocks.database;

public class ProcesoEscritor extends Thread {
	private BaseDeDatos db;
	public ProcesoEscritor(BaseDeDatos baseDeDatos){
		this.db = baseDeDatos;
	}

	@Override
	public void run() {
		try {
			int pos = (int)getId()% db.size();
			System.err.println("Escritura en "+pos);
			db.escribir(pos, "--Contenido modificado por "+getId());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
