package Individual;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

public class Mutation {

	private static int r1;
	private static int r2;
	
	public static List<Integer> Realocate(List<Integer> pai) {

		int tam = pai.size();
		RandomIntervalo(tam);
		List<Integer> res = new ArrayList<Integer>(pai);

		// Substitui o valor que estava na posição r2 até a posicção
		// r1+1 e desloca os outros que estavam entre r1 e r2 para direita
		for (int i = 0; (r2 - i) > r1; i++) {
			int aux = res.get(r2 - i);
			res.set(r2 - i, res.get(r2 - (i + 1)));
			res.set(r2 - (i + 1), aux);
		}

		return res;
	}

	
	public static List<Integer> Swap(List<Integer> pai) {

		int tam = pai.size();
		RandomIntervalo(tam);
		List<Integer> res = new ArrayList<Integer>(pai);

		// Substitui os valores
		int aux = res.get(r1);
		res.set(r1, res.get(r2));
		res.set(r2, aux);

		return res;
	}

	
	public static List<Integer> Scramble(List<Integer> pai) {

		int tam = pai.size();
		RandomIntervalo(tam);
		List<Integer> res = new ArrayList<Integer>(pai);

		int n = 15;
				
		for(int i=0; i < n; i++) {
			int ri1 = ThreadLocalRandom.current().nextInt(r1, r2+1);
			int ri2 = ThreadLocalRandom.current().nextInt(r1, r2+1);
			
			int aux = res.get(ri1);
			res.set(ri1, res.get(ri2));
			res.set(ri2, aux);
		}
		
		return res;
	}
	
	public static List<Integer> Two_Opt(List<Integer> pai) {

		int tam = pai.size();
		RandomIntervalo(tam);
		List<Integer> res = new ArrayList<Integer>(pai);
		
		for(int ri1 = r1, ri2 = r2 ; ri1 < ri2 ; ri1++, ri2--) {
			int aux = res.get(ri1);
			res.set(ri1, res.get(ri2));
			res.set(ri2, aux);
		}
		
		return res;
	}
	
	
	/*
	 * Funcao RandomIntervalo: Gera dois valores positivos inteiros aleatoriamente,
	 * num intervalo de 0 ate tamanho determinado
	 */
	
	public static void RandomIntervalo(int tamanho) {

		int a, b, aux;
		// Gera dois numeros distintos, aleatoriamente, menores que o tamanho do
		// pai
		do {
			a = ThreadLocalRandom.current().nextInt(0, tamanho);
			b = ThreadLocalRandom.current().nextInt(0, tamanho);
		} while (a == b);

		// Garante que a será sempre o menor numero
		if (a > b) {
			aux = a;
			a = b;
			b = aux;
		}

		r1 = a;
		r2 = b;
	}

}