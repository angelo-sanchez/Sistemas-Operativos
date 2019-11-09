package deadlocks.barberiaConcurrente;

public class MainBarberia {
	public static void main(String[] args) {
		Barberia barberia = new Barberia();
		Barbero barbero = new Barbero(barberia);
		Cliente[] clientes = new Cliente[10];
		barbero.start();
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(barberia);
			clientes[i].start();
		}
	}
}