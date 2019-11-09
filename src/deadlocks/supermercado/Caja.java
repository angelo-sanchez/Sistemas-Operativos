package deadlocks.supermercado;

public class Caja extends Thread {
	private Supermercado supermercado;
	private Productos[] changuito;
	public Caja(Supermercado supermercado) {
		this.supermercado = supermercado;
		this.changuito = new Productos[15];
	}

	@Override
	public void run() {
		while (true){
			generarChanguito();
			try {
				for(Productos p : changuito){
					supermercado.venderProducto(p);
					System.out.print("+++Caja "+getId()+" sacó "+p.toString()+'\n');
				}
				sleep(500); // Tiempo en el que se está cobrando
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void generarChanguito(){
		Productos[] productos = Productos.values();
		int prod;
		for(int i = 0; i < changuito.length; i++){
			prod = (int)(Math.random()* productos.length);
			changuito[i] = productos[prod];
		}
	}
}
