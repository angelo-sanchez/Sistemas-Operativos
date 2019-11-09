package threads;

public class HelloWorld2 extends Thread {
	private int n;
	private long id;
	public HelloWorld2(){
		super();
		this.n=1000;
		this.id = getId();
	}
	public void run(){
		for (int i = 1; i <= n; i++) {
			System.out.println("Hola threads! Soy el thread "+id+". Ejecutando por "+i+"a vez");
		}
	}
}
