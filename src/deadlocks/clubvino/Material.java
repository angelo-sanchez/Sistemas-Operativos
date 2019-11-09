package deadlocks.clubvino;

import static deadlocks.clubvino.Equipo.*;

public class Material {
	private int[] recursos;
	public Material(int njarras, int nUnidadFermentacion, int nEstacionMezcla){
		this.recursos = new int[values().length];
		recursos[JARRA.ordinal()] = njarras;
		recursos[UNIDAD_FERMENTACION.ordinal()] = nUnidadFermentacion;
		recursos[ESTACION_MEZCLA.ordinal()] = nEstacionMezcla;
	}
	public synchronized void agarrar(long idMiembro, Equipo recurso, int cantidad) throws InterruptedException {
		while (this.recursos[recurso.ordinal()] < cantidad){
			// No se puede conseguir n unidades del recurso
			System.err.print("Miembro "+idMiembro+" no puede conseguir " + cantidad + " unidades de " + recurso + '\n');
			wait();
		}
		System.err.print("Miembro "+idMiembro+" logró conseguir el recurso: "+recurso+" solicitado \n");
		recursos[recurso.ordinal()] -= cantidad;
	}
	public synchronized void desocupar(long idMiembro, Equipo recurso, int cantidad){
		System.err.print("Miembro "+idMiembro+" libera el uso de "+recurso+'\n');
		recursos[recurso.ordinal()] += cantidad;
		notifyAll();
	}
}
