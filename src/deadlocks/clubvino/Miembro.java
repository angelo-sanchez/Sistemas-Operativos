package deadlocks.clubvino;

import static deadlocks.clubvino.Equipo.*;

public class Miembro extends Thread {
	private final Almacen almacen;
	private final Material material;

	public Miembro(Almacen almacen, Material material){
		this.material = material;
		this.almacen = almacen;
	}
	private boolean hacerVino() throws InterruptedException {
		System.out.print(getId()+" empieza a hacer su vino\n");
		etapaInicial();
		etapaFermentacion();
		etapaDecantacion();
		System.out.print(getId()+" ha terminado de hacer su vino\n\t\t\tA degustar!\n");
		return true;
	}

	private void etapaFermentacion() throws InterruptedException {
		// 4.a) Ir a Unidad de Fermentacion
		material.agarrar(getId(), UNIDAD_FERMENTACION, 1);
		// 4.b) Dejar Jarra
		material.desocupar(getId(), JARRA, 1);
		// 4.c)
		System.out.print("El vino de "+getId()+" se está fermentando\n");
		sleep(10000);
		material.desocupar(getId(), UNIDAD_FERMENTACION, 1);
		System.out.print("El vino de "+getId()+" terminó la maduración\n");
	}

	private void etapaDecantacion() throws InterruptedException {
		// 5) Agarrar dos jarras
		material.agarrar(getId(), JARRA, 2);
		System.out.print("El miembro "+getId()+" está decantando el vino\n");
		sleep(1000);
		material.desocupar(getId(), JARRA, 2);
	}

	private void etapaInicial() throws InterruptedException {
		// 1) Agarrar E. Mezcla
		material.agarrar(getId(), ESTACION_MEZCLA, 1);
		// 2) Agarrar una jarra, 2 jugos y una levadura
		material.agarrar(getId(), JARRA, 1);
		almacen.agarrar(getId(), 2, 1);
		// 3) Desocupar Estacion de Mezcla
		material.desocupar(getId(), ESTACION_MEZCLA, 1);
	}

	@Override
	public void run() {
		while (true){
			try {
				if(hacerVino()) {
					sleep(5000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
