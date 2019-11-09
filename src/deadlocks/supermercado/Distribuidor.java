package deadlocks.supermercado;

public class Distribuidor extends Thread {
	private Supermercado supermercado;
	private Productos producto;
	public Distribuidor(Supermercado supermercado, Productos producto) {
		this.supermercado = supermercado;
		this.producto = producto;
	}

	@Override
	public void run() {
		while(true){
			try {
				supermercado.agregarProducto(producto);
				System.out.print("+++Distribuidor "+getId()+" agregó "+ producto.toString()+'\n');
				sleep(5000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
