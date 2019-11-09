package deadlocks.clubvino;

public class ClubDeVino {
	public static void main(String[] args) {
		Almacen alm = new Almacen(15, 20);
		Material mat = new Material(6, 7, 2);
		////////////////
		Administrador admin = new Administrador(alm);
		admin.start();
		/////////////
		Miembro[] miembros = new Miembro[8];
		for (int i = 0; i < miembros.length; i++) {
			miembros[i] = new Miembro(alm, mat);
			miembros[i].start();
		}
	}
}
