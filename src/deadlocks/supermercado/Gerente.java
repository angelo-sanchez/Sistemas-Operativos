package deadlocks.supermercado;

public class Gerente extends Thread {
	private final Supermercado supermercado;

	public Gerente(Supermercado supermercado){
		this.supermercado = supermercado;
	}

	@Override
	public void run() {
		int op = 0;
		Supermercado.Operacion[] operacions = Supermercado.Operacion.values();
		Productos[] productos = Productos.values();
		while (true) {
			long descanso = (long)(Math.random()*3000);
			int prod = (int) (Math.random() * productos.length);
			try {
				Supermercado.Operacion operacion = operacions[op%operacions.length];
				supermercado.modificarCanasta(operacion, productos[prod]);
				// Imprime: +++Gerente [AGREGA/QUITA] [producto]
				System.out.print("+++Gerente "+operacion.toString()+' '+ productos[prod].toString()+'\n');
				op++;
				sleep(descanso);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
