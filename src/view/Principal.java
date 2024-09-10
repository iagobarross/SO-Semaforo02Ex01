package view;

import java.util.concurrent.Semaphore;
import controller.ThreadCavaleiro;

public class Principal {

	public static void main(String[] args) {
		int permissoes = 1;
		Semaphore semaforo = new Semaphore(permissoes);
		for(int i = 0; i < 4; i++) {
			Thread t = new ThreadCavaleiro((i+1), semaforo);
			t.start();
		}
		
		
	}

}
