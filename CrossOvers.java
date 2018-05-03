package Individual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CrossOvers {

	private static List<Integer> filho1;
	private static List<Integer> filho2;

	private static List<Integer> PMX(List<Integer> pai1, List<Integer> pai2) {

		// Lista usada para verificar se existe mais de um elemento com mesmo
		// valor
		List<Integer> posRepetidas = new ArrayList<Integer>();

		HashMap hm1 = new HashMap();
		HashMap hm2 = new HashMap();

		int tam = pai1.size();
		List<Integer> filho = new ArrayList<Integer>();

		for (int i = 0; i < tam; i++) { // Inicializar ArrayList com null (pra
										// poder acessar as posicoes)
			filho.add(null);
			posRepetidas.add(null);

			// Associa cada valor dos elementos do pai1 a uma string atraves de
			// um hashmap. A string eh
			// representada por "N" mais um sufixo, sendo este, um valor inteiro
			// de 1 ate o tamanho dos pais
			Integer valor = pai1.get(i);
			hm1.put("N" + (i + 1), valor);
		}

		// Vetor de string que guarda a ordem em que os elementos do pai2
		// aparecem
		// identificados pela String que identifica o hashmap

		// TODO String[] ordem_elementos_pai2 = new String[pai2.size()];

		ArrayList<String> ordem_elementos_pai2 = new ArrayList<String>();

		for (int i = 1; i <= tam; i++) {

			Integer valor = pai2.get(i - 1); // Pega o valor da primeira posicao
												// de pai2

			int indice = pai1.indexOf(valor); // Busca a posicao desse valor em
												// pai1

			// Se este valor apareceu uma unica vez
			if (posRepetidas.contains(indice) == false) {
				hm2.put("N" + (indice + 1), valor);
				posRepetidas.add(indice);
				ordem_elementos_pai2.add("N" + (indice + 1));
			}

			else {
				int lastPos = indice; // Guarda a posicao da primeira ocorrencia
										// do valor repetido em pai1

				List<Integer> copia = new ArrayList<Integer>();

				do {
					// Copia sublista, a partir da posicao seguinte do indice
					// guardado ate pai1.size() (porque eh exclusivo)
					copia = pai1.subList(lastPos + 1, tam);

					// Procura proxima ocorrendia do elemento na sublista,
					// retorna -1 se nao encontrar
					lastPos = copia.indexOf(valor);
					System.out.println("\t\tLAST POS\t" + lastPos);

				} while (posRepetidas.contains(indice + (lastPos + 1)) == true);

				if (lastPos != -1) {
					// Guarda posicao da nova ocorrencia, em relacao a sua
					// posicao na lista pai1
					indice = indice + (lastPos + 1);
					posRepetidas.add(indice);
					hm2.put("N" + (indice + 1), valor);
					ordem_elementos_pai2.add("N" + (indice + 1));
				}
			}
		}

		// TODO TIRAR
		// ArrayList<Integer> ValoresInseridosFilho = new ArrayList<Integer>();

		int a, b, aux;
		// Gera dois numeros distintos, aleatoriamente, menores que o tamanho do
		// pai
		do {
			a = ThreadLocalRandom.current().nextInt(0, tam);
			b = ThreadLocalRandom.current().nextInt(0, tam);
		} while (a == b);

		// Garante que a será sempre o menor numero
		if (a > b) {
			aux = a;
			a = b;
			b = aux;
		}

		// Vetor de string que guarda as trocas ocorridas no crossover atraves
		// das strings que
		// identificam os hashmas
		ArrayList<String> ValoresInseridosFilho = new ArrayList<String>();

		// Seleciona conjunto de elementos do pai1 e copia para filho,
		// em suas posicoes respectivas
		for (int i = a; i < b; i++) {

			ValoresInseridosFilho.add("N" + (i + 1));
			filho.set(i, (Integer) hm1.get("N" + (i + 1)));

			// TODO filho.set(i, pai1.get(i));
			// TODO ValoresInseridosFilho.add(pai1.get(i));
		}

		// Procura os elementos do pai2 nas mesmas
		// posicoes para tentar inseri-los

		// TODO for (int i = 0; i < (tam / 2); i++) {

		for (int i = a; i < b; i++) {

			// TODO String elemento = ordem_elementos_pai2.get(i);

			String Elemento_de_Insercao = "N" + (i + 1);
			String Elemento_de_Referencia = "N" + (i + 1);
			int Posicao_de_Insercao = ordem_elementos_pai2.indexOf(Elemento_de_Referencia);

			//Caso elemento ainda nao tenha sido inserido em filho
			if (ValoresInseridosFilho.contains(Elemento_de_Insercao) == false) {
				
				int j = 0;
				
				while (j < tam) {
					
					// Caso a posicao esteja livre, elemento do pai2 eh inserido
					if (filho.get(Posicao_de_Insercao) == null) {
						ValoresInseridosFilho.add(Elemento_de_Insercao);
						filho.set(Posicao_de_Insercao, (Integer) hm2.get(Elemento_de_Insercao));
						break;
					}
					
					// Caso a posicao ja tenha sido preenchid,
					// procura-se pelo novo elemento equivalente
					else {
						Elemento_de_Referencia = "N" + (Posicao_de_Insercao+1);
						Posicao_de_Insercao = ordem_elementos_pai2.indexOf(Elemento_de_Referencia);
					}
					j++;
				}
			}
			
			// TODO int elemento = pai1.get(a + i);
		}

		// Completa o resto das posicoes vazias com os elementos do pai2
		for (int i = 0; i < a; i++) {
			
			if (filho.get(i) == null){
				String elemento = ordem_elementos_pai2.get(i);
				filho.set(i, (Integer) hm2.get(elemento));
			}
		}
		
		for (int i = b; i < tam; i++) {
			if (filho.get(i) == null){
				String elemento = ordem_elementos_pai2.get(i);
				filho.set(i, (Integer) hm2.get(elemento));
			}
		}
		
		return filho;
	}

	public void ParcialmenteMapeado(List<Integer> pai1, List<Integer> pai2) {
		filho1 = PMX(pai1, pai2);
		filho2 = PMX(pai2, pai1);
	}

	public List<Integer> getF1() {
		return filho1;
	}

	public List<Integer> getF2() {
		return filho2;
	}

}
