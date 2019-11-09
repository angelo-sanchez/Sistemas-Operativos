package deadlocks.database;

public class ProcesoLector extends Thread {
	BaseDeDatos db;
	public ProcesoLector(BaseDeDatos baseDeDatos){
		this.db = baseDeDatos;
	}

	@Override
	public void run() {
		try {
			int pos = (int)getId()% db.size();
			System.err.println("Lectura en "+pos);
			System.out.print(db.leer(pos)+" leido por: "+getId()+" en la posicion "+pos+'\n');
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
