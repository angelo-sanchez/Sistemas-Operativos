package deadlocks.blancanieves;


public class Enano extends Thread {
	private Casa casita;
	private final String nombre;

	public Enano(Casa casita, String nombre) {
		this.casita = casita;
		this.nombre = nombre;
	}

	@Override
	public void run() {
		while(true) {
			try {
				casita.entrar(this.nombre);
				trabajar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void trabajar() throws InterruptedException {
		System.out.print(nombre+" se va a trabajar\n");
		Thread.sleep(5000);
	}
}
