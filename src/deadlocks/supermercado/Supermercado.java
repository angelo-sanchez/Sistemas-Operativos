package deadlocks.supermercado;

import java.util.HashMap;

public class Supermercado {
	public enum Operacion {AGREGA, QUITA}

	private HashMap<Productos, ControlStockProducto> canasta;
	private final int max;
	private final double porcentaje;

	public Supermercado(int stockMax, double porcentaje){
		this.canasta = new HashMap<>();
		this.porcentaje = porcentaje;
		this.max = stockMax;
	}
	public synchronized void agregarProducto(Productos producto) throws InterruptedException {
		if(this.canasta.containsKey(producto)) { // Si existe el producto
			ControlStockProducto cp = this.canasta.get(producto);
			while(cp.getStockDeposito() == max){ // Si estoy completo de este producto
				System.err.print("\tEl producto "+producto.toString()+"está completo");
				wait(); // Freno al proveedor
			}
			cp.aumentarStockDeposito();
		}else {
			this.canasta.put(producto, new ControlStockProducto(producto.toString(), 1));
			notifyAll(); // Agregúé un producto, ya puede continuar la caja
		}
		System.out.print("\tSe agregó el producto "+producto.toString()+'\n');
	}
	public synchronized void venderProducto(Productos producto) throws InterruptedException {
		while (!this.canasta.containsKey(producto)) {
			System.err.print("TODAVIA NO VENDEMOS "+ producto.toString()+'\n');
			wait();
		}
		ControlStockProducto stock = this.canasta.get(producto);
		while(stock.getStockCanasta() == 0){ // Si no hay en canasta el producto que necesito
			System.err.print("Hace falta reponer "+ producto.toString()+'\n');
			wait(); // Espero a que repongan
		}
		stock.retirarUno();
		System.out.print("Se vende una unidad de "+ producto.toString()+'\n');
		if(stock.getStockCanasta() < max*porcentaje) // Si estoy bajo la linea de reposicion
			notifyAll(); // Llamo a los proveedores o al gerente
	}
	public void modificarCanasta(Operacion operacion, Productos producto) throws InterruptedException {
		switch (operacion){
			case AGREGA:
				reponerProducto(producto);
				break;
			case QUITA:
				retirarProducto(producto);
				break;
		}
	}
	public synchronized void reponerProducto(Productos producto) throws InterruptedException {
		while(!this.canasta.containsKey(producto)){
			System.err.print("TODAVIA NO VENDEMOS "+ producto.toString()+", dijo el Gerente\n");
			wait();
		}
		ControlStockProducto stockProducto = this.canasta.get(producto);
		while(stockProducto.getStockDeposito() == 0){ // Si no hay en el deposito el producto
			notifyAll(); // Llamo a los distriubidores
			System.err.print("No hay en el depósito "+producto+", Llamo a los proveedores\n");
			wait(); // Espero que carguen el depósito
		}
		// Ahora que ya hay, repongo
		stockProducto.ponerEnCanasta((int)(Math.random()*stockProducto.getStockDeposito()));
	}
	public synchronized void retirarProducto(Productos producto) throws InterruptedException {
		while(!this.canasta.containsKey(producto)){
			System.err.print("SE INTENTA ELIMINAR "+ producto.toString()+" PERO NO EXISTE");
			wait();
		}
		ControlStockProducto stockProducto = this.canasta.get(producto);
		stockProducto.quitarDeCanasta();
	}
	public synchronized void quitarVencidos(int diasPermitido){
		for(ControlStockProducto stockProducto : this.canasta.values()){
			if(stockProducto.tiempoEnCanasta() >= diasPermitido){
				System.err.print("Se quita el producto "+stockProducto.getNombre()+" por tiempo\n");
				stockProducto.quitarDeCanasta();
				continue;
			}
			stockProducto.avanzaTiempoEnCanasta();
		}
	}
}
