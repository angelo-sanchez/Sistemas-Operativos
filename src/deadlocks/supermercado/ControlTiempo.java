package deadlocks.supermercado;

public class ControlTiempo extends Thread{
	private final Supermercado supermercado;
	private final int dias;
	public ControlTiempo(Supermercado supermercado, int diasVencimiento){
		this.supermercado = supermercado;
		this.dias = diasVencimiento;
	}
	@Override
	public void run() {
		while (true){
			supermercado.quitarVencidos(dias);
			try {
				sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
