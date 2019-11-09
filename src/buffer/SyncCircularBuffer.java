package buffer;
/** Este código es distribuido como parte de un trabajo práctico de
 *  la materia Sistemas Operativos I dictada por la de Ciencias Exactas de
 *  la Universidad nacional del centro de la provincia de Buenos Aires (UNICEN).
 *  El código no debe usarse en ningún otro proyecto debido a que contiene o
 *  puede contener malas prácticas y errores introducidos intencionalmente con
 *  fines didácticos. Así mismo el código carece de cualquier tipo de optimización
 *  primando la legibilidad del mismo.
 *  @author Dr. Juan Manuel Rodriguez
 */

public class SyncCircularBuffer<T> implements IBuffer<T> {

	private Object[] elements;
	/**
	 * Posicion del elemento que voy a devolver.
	 */
	private int posNext;
	/**
	 * Posicion del elemento que voy a insertar.
	 */
	private int lastElem;
	/**
	 * Crea un buffer de 10 elementos
	 */
	public SyncCircularBuffer(){
		this(10);
	}
	/**
	 * Crea un buffer de size elementos
	 * @param size
	 */
	public SyncCircularBuffer(int size){
		this.elements = new Object[size];
		this.lastElem = 0;
		this.posNext = 0;
	}
	@Override
	public synchronized T next() {
		// Si quiero devolver un elemento que todavía no inserté
		while(this.posNext==this.lastElem){
			try {
				this.wait(); //A dormir...
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		@SuppressWarnings("unchecked")
		T e = (T) this.elements[this.posNext];
		this.posNext=(this.posNext+1)%this.elements.length;
		this.notifyAll();
		return e;
	}

	@Override
	public synchronized void add(T data) {
		// Si quiero pisar un elemento que todavía no devolví
		while(((this.posNext+1)%this.elements.length)==this.lastElem){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int p = this.lastElem;
		this.lastElem=(this.lastElem+1)%this.elements.length;
		this.elements[p] = data;
		this.notifyAll();
	}

	@Override
	public int size() {
		return (this.lastElem-this.posNext)%this.elements.length;
	}

	@Override
	public int maxElements() {
		return this.elements.length;
	}

}
