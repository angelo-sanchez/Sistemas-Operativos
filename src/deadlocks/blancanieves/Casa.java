package deadlocks.blancanieves;

public class Casa {
	private final boolean[] libres;
	private final boolean[] atendidos;
	private int lugares;

	public Casa(int cantSillas) {
		this.atendidos = new boolean[cantSillas];
		this.libres = new boolean[cantSillas];
		for (int i = 0; i < cantSillas; i++) {
			libres[i]=true;
			atendidos[i]=true;
		}this.lugares = cantSillas;
	}

	public synchronized void entrar(String enano) throws InterruptedException {
		while (lugares == 0) { // Si la casa está llena
			wait(); //Espero para entrar
		}
		for (int i = 0; i < libres.length; i++) {
			if (libres[i]) { //Si consigo lugar
				libres[i] = false; // Ocupo una silla
				atendidos[i]=false;
				lugares--; // Decremento la cantidad de lugares
				notifyAll(); // Aviso a Blancanieves
				System.err.print(enano + " esperando que le den la papona\n");
				System.out.print("Quedan "+lugares+" lugares\n");
				while(!atendidos[i]){
					wait(); // Espero a que me atienda
				}
				// Se supone que ya me atendió
				libres[i] = true; // Desocupo la silla
				lugares++; // Aumento la cantidad de lugares
				System.err.print(enano + " ya comió, se wa trabajar\n");
				notifyAll(); // Aviso a los otros enanos para que puedan entrar
				break; //Salgo del for
			}
		}

	}

	public synchronized void atender(int i) throws InterruptedException {
		//Si no hay enanos en la casa
		while (vacia())
			wait(); // Espero que venga uno (o invito al Principe)
		atendidos[i % atendidos.length] = true;
		notifyAll(); // Doy de comer a un enano
	}
	public synchronized boolean vacia(){
		return this.lugares == libres.length;
	}
}
