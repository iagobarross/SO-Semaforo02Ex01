/*1. Simular em Java:
4 cavaleiros caminham por um corredor, simultaneamente, de 2 a 4 m por 50 ms. O corredor �
escuro, tem 2 km e em 500 m, h� uma �nica tocha. O cavaleiro que pegar a tocha, aumenta sua
velocidade, somando mais 2 m por 50 ms ao valor que j� fazia. Em 1,5 km, existe uma pedra
brilhante. O cavaleiro que pegar a pedra, aumenta sua velocidade, somando mais 2 m por 50 ms
ao valor que j� fazia (O cavaleiro que j� det�m a tocha n�o poder� pegar a pedra). Ao final dos 2
km, abrem uma porta rand�mica km, os cavaleiros se separam com 4 portas e, um por vez pega
uma porta aleat�ria (que n�o pode repetir) e entra nela. Apenas uma porta leva � sa�da. As outras
3 tem monstros que os devoram.
*/
package controller;

import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {
	private static boolean tocha = true;
	private static boolean pedra = true;
	private int idCavaleiro;
	private static int distanciaTotal = 2000;
	private int bonus = 0;
	private int itensColetados = 0;
	private Semaphore semaforo;
	private static int portaFinal = 4;

	public ThreadCavaleiro(int idCavaleiro, Semaphore semaforo) {
		this.idCavaleiro = idCavaleiro;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		caminhar();
		try {
			semaforo.acquire();
			abrirPorta();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			semaforo.release();
		}
	}

	private void caminhar() {
		int distanciaPercorrida = 0;
		while (distanciaPercorrida < distanciaTotal) {
			int passos = (int) ((Math.random() * 2.1) + 2) + bonus;
			distanciaPercorrida += passos;
			System.out.println("O cavaleiro " + idCavaleiro + " andou " + passos + " metros e percorreu ao todo "
					+ distanciaPercorrida + " metros.");
			if (distanciaPercorrida >= 500 && tocha) {
				System.out.println(
						"O cavaleiro " + idCavaleiro + " encontrou uma tocha, assim recebendo um b�nus de velocidade.");
				tocha = false;
				bonus += 2;
				itensColetados++;
			} else if (distanciaPercorrida >= 1500 && itensColetados == 0 && pedra) {
				System.out.println("O cavaleiro " + idCavaleiro
						+ " encontrou uma pedra brilhante, assim recebendo um b�nus de velocidade.");
				pedra = false;
				bonus += 2;
				itensColetados++;
			}

			try {
				sleep(50);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}

	}

	private void abrirPorta() {
		int portaEscolhida = (int) ((Math.random() * 4) + 0);

		if (portaEscolhida == portaFinal && portaFinal == 3) {
			System.out.println("O cavaleiro " + idCavaleiro + " entrou na porta " + portaFinal + " e chegou na sa�da.");
			portaFinal--;
		} else {
			System.out.println("O cavaleiro " + idCavaleiro + " entrou na porta " + portaFinal
					+ " e se deparou com monstros que o devoraram.");
			portaFinal--;
		}
	}
}
