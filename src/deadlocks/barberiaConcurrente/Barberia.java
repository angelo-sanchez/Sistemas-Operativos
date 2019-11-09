package deadlocks.barberiaConcurrente;

public class Barberia {
	private final int cantSillas;
	private int sillasOcupadas;
	private boolean sillaBarberoOcupada = false;
	private boolean barberoDormido = false;
	private Cliente clienteActual;

	public Barberia(int capacidad) {
		this.cantSillas = capacidad;
	}

	public Barberia() {
		this(5);
	}

	public synchronized void entrar(Cliente cliente) throws InterruptedException {
		clienteActual = cliente;
		if (sillasOcupadas == cantSillas) {
			// La barberia está llena.
			System.err.print("--El cliente " + clienteActual.getId() + " se fue sin cortarse el pelo\n");
			return;
		}
		// Hay lugar en la barberia
		sillasOcupadas++;
		System.out.print("El cliente " + clienteActual.getId() + " está esperando su turno\n");
		while (sillaBarberoOcupada) {
			// Pero el barbero está cortando a otro
			wait();
		}
		// Le toca afeitarse al cliente actual.
		sillasOcupadas--;
		sillaBarberoOcupada = true;
		// Pero tiene que despertar al barbero
		if (barberoDormido) {
			System.err.print("-El barbero estaba dormido, lo despertó el cliente " + clienteActual.getId() + '\n');
			notifyAll();
		}
		System.out.print("Al cliente " + clienteActual.getId() + " se lo está afeitando\n");

		sillaBarberoOcupada = false;

		// El que sigue!!
		notifyAll();
	}

	public synchronized void esperarCliente() throws InterruptedException {
		barberoDormido = true;
		while (!sillaBarberoOcupada) {
			// Si no hay nadie en la sala de espera
			System.err.print("++Barbero esperando por un cliente\n");
			wait();
		}
		barberoDormido = false;
	}

	public synchronized void afeitarCliente() {
		System.out.print("Cliente " + clienteActual.getId() + " afeitado\n");
		clienteActual.afeitar();
		clienteActual = null;
		notifyAll();
	}
}
