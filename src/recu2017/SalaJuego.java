package recu2017;

import java.util.concurrent.Semaphore;

public class SalaJuego {
	private final int min;
	private int jugando;
	private boolean sePuedeJugar;
	private int esperando;
	// Semaforo para controlar el acceso a esperando
	private final Semaphore mtxEspera;
	// Semáforo que controla cuando pueden jugar
	private final Semaphore mtxJuego;

	public SalaJuego(int minJugadores){
		this.min = minJugadores;
		this.jugando = 0;
		this.sePuedeJugar = false;
		this.esperando = 0;
		this.mtxEspera = new Semaphore(1);
		this.mtxJuego = new Semaphore(1);
	}

	public synchronized void entrar(long idJugador) throws InterruptedException {
		this.esperando++; // Entro
		notifyAll(); // Aviso que ya llegué
		System.err.print("El jugador "+idJugador+" entró al area de espera\n");
		while(esperando <= min) { // Si hay menos de 4 esperando
			System.err.print("El jugador "+idJugador+" debe esperar que al menos haya "+min+" jugadores en el area de espera\n");
			wait(); // Espero pacientemente
		}
		// Ya hay cuatro en la sala de espera
		if(jugando == 0) {
			System.err.print("Ya puede pasar el proximo grupo de jugadores\n");
			sePuedeJugar = true;
		}
	}
	public synchronized void jugar(long idJugador) throws InterruptedException{
		while (!sePuedeJugar){ // Si hay todavia alguien jugando
			System.err.print("Aún hay algun jugador en la consola, el jugador "+idJugador+" debe esperar\n");
			wait(); // Me toco pacientemente
		}
		esperando--; // Ya no estoy esperando
		jugando++; // Ahora entro a jugar yo papu
		System.err.print("El jugador "+idJugador+" pudo acceder a la consola\n");
		if(jugando == min) { // Si soy el ultimo en entrar
			System.err.print("Ya no quedan controles, los demás jugadores deberán esperar\n");
			sePuedeJugar = false; // Ya no puede entrar nadie más
		}
	}
	public synchronized void salir(long idJugador){
		jugando--; // Me voy
		System.err.print("El jugador "+idJugador+" abandonó la sala\n");
		if(jugando == 0){ // Si soy el ultimo en irse
			System.err.print("Fin del juego\n");
			notifyAll(); // Aviso para que pueda pasar otro grupo
		}
	}
}
