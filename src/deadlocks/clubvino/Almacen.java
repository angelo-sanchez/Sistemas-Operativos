package deadlocks.clubvino;

public class Almacen {
	private int envasesJugo, sobresLevadura;
	private final int maxEnvases, maxSobres;

	public Almacen(int cantEnvases, int cantLevadura){
		this.envasesJugo = cantEnvases;
		this.sobresLevadura =cantLevadura;
		this.maxEnvases = cantEnvases;
		this.maxSobres = cantLevadura;
	}
	public synchronized void agarrar(long idMiembro, int njugo, int nlevadura) throws InterruptedException {
		while(this.envasesJugo < njugo || this.sobresLevadura < nlevadura){
			// Si hay menos envases o sobres de los que necesito
			notifyAll(); // Aviso al administrador
			System.err.print("--\tHace falta jugo o levadura en el almacén, pésimo servicio\n");
			wait(); // Espero a que repongan
		}
		this.envasesJugo -= njugo;
		this.sobresLevadura -= nlevadura;
		System.err.print("Miembro "+idMiembro+" sacó: "+njugo+" jugos y "+nlevadura+" levadura\n");
	}
	public synchronized void reponer() throws InterruptedException {
		while(this.sobresLevadura == maxSobres || this.envasesJugo == maxEnvases) {
			// Si el almacen está lleno
			System.err.print("++\tAlmacen lleno, vuelvo luego\n");
			wait(); // Me voy
		}
		// Ahora que hace falta algo repongo
		System.err.print("Administrador repone: "+(maxSobres-sobresLevadura)+" levadura y "+(maxEnvases-envasesJugo)+" jugo\n");
		sobresLevadura = maxSobres;
		envasesJugo = maxEnvases;
		// Aviso a los miembros que ya repuse
		notifyAll();
	}
}
