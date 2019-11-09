package deadlocks.supermercado;

public class Baratija {
	public static void main(String[] args) {
		Supermercado baratija = new Supermercado(50,.1);
		Gerente gerente = new Gerente(baratija);
		gerente.start();

		final int m = Productos.values().length;
		Distribuidor[] distribuidores= new Distribuidor[m];
		for (int i = 0; i < m; i++) {
			distribuidores[i] = new Distribuidor(baratija, Productos.values()[i]);
			distribuidores[i].start();
		}
		final int n = 10;
		Caja[] cajas = new Caja[n];
		for (int i = 0; i < n; i++) {
			cajas[i] = new Caja(baratija);
			cajas[i].start();
		}

	}
}
