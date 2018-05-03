import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main_Teste_Hash {
	
	private static HashMap hm1 = new HashMap();
	private static HashMap hm2 = new HashMap();
	
	public static void main(String[] args) {
		
		Integer[] p1 = {1,1,2,3,3,4,5,6,7,8,9,6};
		Integer[] p2 = {9,3,7,3,8,1,6,2,6,5,1,4};
		
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> l2 = new ArrayList<Integer>();
		
		for(int i=0; i<p1.length; i++){
			l1.add(p1[i]);
			l2.add(p2[i]);
		}
		
		long start = System.currentTimeMillis();
		
		HASHMAP(l1,l2);
		System.out.println("HM1");
		ExibeHASH(hm1);
		System.out.println("\nHM2");
		ExibeHASH(hm2);
		
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.println("Total time: " + elapsedTime / 1000.0 + "s");
		
	}
	
	public static void HASHMAP(List<Integer> pai1, List<Integer> pai2) {
		
		// Lista usada para verificar se existe mais de um elemento com mesmo valor
		List<Integer> posRepetidas = new ArrayList<Integer>(); 

		int tam = pai1.size();
		List<Integer> filho = new ArrayList<Integer>(); 
		
		for (int i = 0; i < tam; i++) { // Inicializar ArrayList com null (pra
										// poder acessar as posicoes)
			filho.add(null);
			posRepetidas.add(null);

			// Associa cada valor dos elementos do pai1 a uma string atraves de um hashmap. A string eh 
			// representada por "N" mais um sufixo, sendo este, um valor inteiro de 1 ate o tamanho dos pais
			Integer valor = pai1.get(i);
			hm1.put("N" + (i + 1), valor);
		}
		
		for (int i = 1; i <= tam; i++) {

			Integer valor = pai2.get(i - 1); // Pega o valor da primeira posicao de pai2
												

			int indice = pai1.indexOf(valor); // Busca a posicao desse valor em pai1
												
			// Se este valor apareceu uma unica vez
			if (posRepetidas.contains(indice) == false) {
				hm2.put("N" + (indice + 1), valor);
				posRepetidas.add(indice);
			}

			else {
				int lastPos = indice; // Guarda a posicao da primeira ocorrencia do valor repetido em pai1
									
				List<Integer> copia = new ArrayList<Integer>();

				do {
					// Copia sublista, a partir da posicao seguinte do indice guardado ate pai1.size() (porque eh exclusivo)
					copia = pai1.subList(lastPos + 1, tam);

					// Procura proxima ocorrendia do elemento na sublista, retorna -1 se nao encontrar
					lastPos = copia.indexOf(valor);
					
				} while (posRepetidas.contains(indice + (lastPos + 1)) == true);

				if (lastPos != -1) {
					// Guarda posicao da nova ocorrencia, em relacao a sua posicao na lista pai1
					indice = indice + (lastPos + 1);
					posRepetidas.add(indice);
					hm2.put("N" + (indice+1), valor);
				}
			}
		}
	}
	
	public static void ExibeHASH (HashMap hm) {
		
		for(int i=0; i < hm.size(); i++) {
			System.out.print("N"+(i+1)+", ");
			System.out.println(hm.get("N"+(i+1)));
		}
	}

}
