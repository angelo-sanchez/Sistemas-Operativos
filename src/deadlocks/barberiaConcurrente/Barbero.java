package deadlocks.barberiaConcurrente;

public class Barbero extends Thread {
	Barberia b;

	Barbero(Barberia barberia) {
		b = barberia;
	}

	@Override
	public void run() {
		while (true) {
			try {
				b.esperarCliente();
				sleep(1000); // Tiempo que le lleva al barbero afeitar
				b.afeitarCliente();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}