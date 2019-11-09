package deadlocks.supermercado;

public class ControlStockProducto {
	private final String producto;
	private int diasEnCanasta;
	private int stockDeposito;
	private int stockCanasta;

	public ControlStockProducto(String nombre, int stockInicial){
		this.diasEnCanasta = 0;
		this.stockCanasta = 0;
		this.stockDeposito = stockInicial;
		this.producto = nombre;
	}

	public int tiempoEnCanasta() {
		return this.diasEnCanasta;
	}

	public void avanzaTiempoEnCanasta() {
		this.diasEnCanasta++;
	}

	public void aumentarStockDeposito() {
		this.stockDeposito++;
	}

	public int getStockDeposito() {
		return this.stockDeposito;
	}

	public int getStockCanasta() {
		return this.stockCanasta;
	}
	public void retirarUno(){
		this.stockCanasta--;
	}

	public void quitarDeCanasta() {
		this.stockDeposito += this.stockCanasta;
		this.diasEnCanasta = 0;
		this.stockCanasta = 0;
	}
	public void ponerEnCanasta(int cantidad){
		this.avanzaTiempoEnCanasta();
		if(this.stockDeposito <= cantidad)
			cantidad = this.stockDeposito;
		this.stockCanasta += cantidad;
		this.stockDeposito -= cantidad;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof String))
			return false;
		String producto1 = (String) o;
		return producto.equals(producto1);
	}

}
