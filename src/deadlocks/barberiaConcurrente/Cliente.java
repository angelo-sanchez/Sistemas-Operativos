package deadlocks.barberiaConcurrente;

public class Cliente extends Thread {
	private boolean afeitado = false;
	private Barberia b;

	Cliente(Barberia barberia) {
		this.b = barberia;
	}

	public void afeitar() {
		afeitado = true;
	}

	@Override
	public void run() {
		while (!afeitado) try {
			b.entrar(this);
			// Si estaba llena la barberia, vuelvo a intentar después
			if (!afeitado)
				sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}