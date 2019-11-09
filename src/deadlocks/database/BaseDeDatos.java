package deadlocks.database;

import java.util.concurrent.Semaphore;

public class BaseDeDatos {
	private String[] registros;
	//Alguien está leyendo la base de datos o desea leer
	private int lectores = 0;
	// Controla el acceso a los lectores
	private Semaphore leyendo = new Semaphore(1);
	// Controla el acceso a la base de datos
	private Semaphore dbmtx = new Semaphore(1);
	BaseDeDatos(int capacidad){
		this.registros = new String[capacidad];
	}

	public String leer(int registro) throws InterruptedException {
		leyendo.acquire(); // Acceso exclusivo para incrementar lectores
		lectores++;
		if(lectores == 1){
			// Si es el primer lector
			dbmtx.acquire(); // Nadie puede escribir.
		}
		leyendo.release();
		String dato = registros[registro]; // Accedo a los datos
		leyendo.acquire(); // Acceso exclusivo para decrementar lectores
		lectores--;
		if(lectores == 0)
			// Si soy el ultimo lector
			dbmtx.release(); // Ya se puede escribir
		leyendo.release();
		return dato;
	}
	public int size(){
		return this.registros.length;
	}
	public void escribir(int registro, String contenido) throws InterruptedException {
		dbmtx.acquire();
		this.registros[registro] = contenido;
		dbmtx.release();
	}
}
