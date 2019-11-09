package deadlocks.blancanieves;

public class Blancanieves extends Thread {
	private Casa casita;

	public Blancanieves(Casa casita) {
		this.casita = casita;
	}

	@Override
	public void run() {
		int i = 0;
		while(true){
			try {
				casita.atender(i);
				if(casita.vacia())
				this.pasear();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	private void pasear() throws InterruptedException {
		System.out.print("Me voy de paseo con el Principe ;)\n");
		Thread.sleep(1000);
	}
}
