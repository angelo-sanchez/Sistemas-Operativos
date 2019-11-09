package deadlocks.blancanieves;

public class BlancanievesYEnanitos {
	public static void main(String[] args) {
		Casa casita = new Casa(4);
		String nombres[] = {"Enojado", "Feliz", "Estornudo", "Sabio", "Tontin", "Dormilon", "Timido"};
		Enano[] enanitos = new Enano[nombres.length];
		Blancanieves blancanieves = new Blancanieves(casita);
		for(int i = 0; i < nombres.length; i++){
			enanitos[i] = new Enano(casita, nombres[i]);
			enanitos[i].start();
		}
		blancanieves.start();
	}
}
