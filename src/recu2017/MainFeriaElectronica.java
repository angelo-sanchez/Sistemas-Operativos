package recu2017;

public class MainFeriaElectronica {
	public static void main(String[] args) {
		SalaJuego salaJuego = new SalaJuego(4);
		Jugador[] jugadores = new Jugador[10];

		for (int i = 0; i < jugadores.length; i++) {
			jugadores[i] = new Jugador(salaJuego);
			
			// jugadores[i].setDaemon(true);
			jugadores[i].start();
		}
	}
}
