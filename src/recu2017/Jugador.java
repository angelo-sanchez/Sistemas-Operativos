package recu2017;

public class Jugador extends Thread {
	SalaJuego sala;

	public Jugador(SalaJuego salaJuego){
		this.sala = salaJuego;
	}
	@Override
	public void run() {
		while(true){
			try {
				sala.entrar(getId());
				sala.jugar(getId());
				System.out.print("Jugador "+getId()+": Mira mamá, estoy jugando!!\n");
				sleep((long)(Math.random()*getId()*1000)); // juega durante un iempo aleatorio para hacerlo divertido
				System.out.print("Jugador "+getId()+": Siempre pierdo, me voy a la wea!!\n");
				sala.salir(getId());
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
