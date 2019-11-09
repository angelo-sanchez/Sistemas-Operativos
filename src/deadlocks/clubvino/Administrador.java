package deadlocks.clubvino;

public class Administrador extends Thread {
	private final Almacen almacen;
	
	public Administrador(Almacen almacen){
		this.almacen = almacen;
	}

	@Override
	public void run() {
		while(true){
			try {
				almacen.reponer();
				System.out.print("ADMINISTRADOR "+getId()+": ya repuse\n");
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
