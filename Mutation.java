package Individual;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Mutation {

	public static ArrayList<Integer> Realocate(ArrayList<Integer> pai) {

		int tam = pai.size();
		int r1, r2, aux;
		ArrayList<Integer> res = (ArrayList<Integer>) pai.clone();

		// Gera dois numeros distintos, aleatoriamente, menores que o tamanho do pai
		do {
			r1 = ThreadLocalRandom.current().nextInt(0, tam);
			r2 = ThreadLocalRandom.current().nextInt(0, tam);
		} while (r1 == r2);

		// Garante que r1 será sempre o menor numero
		if (r1 > r2) {
			aux = r1;
			r1 = r2;
			r2 = aux;
		}

		// Substitui o valor que estava na posição r2 até a posicção
		// r1+1 e desloca os outros que estavam entre r1 e r2 para direita
		for (int i = 0; (r2 - i) > r1; i++) {
			aux = res.get(r2 - i);
			res.set(r2 - i, res.get(r2 - (i + 1)));
			res.set(r2 - (i + 1), aux);
		}

		return res;
	}

	public static ArrayList<Integer> Swap(ArrayList<Integer> pai) {

		int tam = pai.size();
		int r1, r2, aux;
		ArrayList<Integer> res = (ArrayList<Integer>) pai.clone();

		// Gera dois numeros distintos, aleatoriamente, menores que o tamanho do pai
		do {
			r1 = ThreadLocalRandom.current().nextInt(0, tam);
			r2 = ThreadLocalRandom.current().nextInt(0, tam);
		} while (r1 == r2);

		// Substitui os valores
		aux = res.get(r1);
		res.set(r1, res.get(r2));
		res.set(r2, aux);

		return res;
	}

	public static ArrayList<Integer> Two_Opt(ArrayList<Integer> pai) {

		int tam = pai.size();
		int r1, r2, aux;
		ArrayList<Integer> res = (ArrayList<Integer>) pai.clone();

		// Gera dois numeros distintos, aleatoriamente, menores que o tamanho do pai
		do {
			r1 = ThreadLocalRandom.current().nextInt(0, tam);
			r2 = ThreadLocalRandom.current().nextInt(0, tam);
		} while (r1 == r2);

		// Garante que r1 será sempre o menor numero
		if (r1 > r2) {
			aux = r1;
			r1 = r2;
			r2 = aux;
		}
		
		int n = 25;
				
		for(int i=0; i < n; i++) {
			int ri1 = ThreadLocalRandom.current().nextInt(r1, r2+1);
			int ri2 = ThreadLocalRandom.current().nextInt(r1, r2+1);
			
			aux = res.get(ri1);
			res.set(ri1, res.get(ri2));
			res.set(ri2, aux);
		}
		
		return res;
	}

}
