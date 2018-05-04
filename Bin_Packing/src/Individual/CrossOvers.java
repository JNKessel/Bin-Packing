package Individual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CrossOvers {
	
	private static List<Integer> filho1;
	private static List<Integer> filho2;
	private int intervaloMin;
	private int intervaloMax;
	
	
	/*
	 * Funcao PMX: aplica o metodo de Crossover Parcialmente Mapeado, a partir
	 * de dois pais e gera um filho. Recebe dois inteiros, representando o
	 * intervalo que sera copiado do pai1 pro filho1 e do pai2 pro filho2
	 * 
	 */
	
	private static List<Integer> PMX (List<Integer> pai1, List<Integer> pai2, int a, int b, HashMap<String, Integer> Hash_Items ) {
//		System.out.println("PMX Inicializacao");
		int tamanho = pai1.size();
		List<Integer> filho = new ArrayList<Integer>(); // Filho tem o mesmo tamanho do pai
		for(int i = 0; i < tamanho; i++) { // Inicializar ArrayList com null (pra poder acessar as posicoes)
			filho.add(null);
		}
		
		//Obtem a representacao dos dois pais como strings, sem valores duplicados
		String[] pai1_String = ConvertToString(Hash_Items, pai1);
		String[] pai2_String = ConvertToString(Hash_Items, pai2);
		
		//ArrayList que ira guardar quais valores ja foram inseridos no filho
		List<String> ValoresInseridos = new ArrayList<String>();
		
//		System.out.println("PMX PT1");
		
		//Seleciona parte dos elementos do pai1 filho, em suas respectivas posicoes  
		for(int i=a ; i<b ; i++) {
			filho.set(i,(Hash_Items.get(pai1_String[i])));
			ValoresInseridos.add(pai1_String[i]);
		}
		
//		System.out.println("PMX PT2");
		//Procura os elementos do pai2 nas mesmas posicoes para tentar inseri-los
		for(int i=a; i < b; i++) {
//			System.out.println("PMX FOR");
			
			String Elemento_de_Insercao = pai2_String[i]; //valor do pai2 a ser inserido
			String Elemento_de_Referencia = pai1_String[i]; //valor do pai1 a ser buscado no pai2
			
			if(ValoresInseridos.contains(Elemento_de_Insercao) == false) {
				int j = 0;
				
				while(j < tamanho) {
//				System.out.println("PMX WHILE");
					
					//Caso a posicao do elemento do pai1 procurado, seja encontrada no vetor pai2
					if( Elemento_de_Referencia == pai2_String[j] ) {
						if (filho.get(j) == null) {
							//Caso a posicao esteja livre, o elemento do pai2 eh inserido
							filho.set(j,(Hash_Items.get(Elemento_de_Insercao)));
							ValoresInseridos.add(Elemento_de_Insercao);
							break;
						}
						
						else { //Caso a posicao ja tenha sido preenchida
							//Procura-se pelo valor do pai1 correspondente a essa posicao no pai2
							Elemento_de_Referencia = pai1_String[j]; 
							j = 0;
						}
					}
					else
						j++;
				}
//				System.out.println("PMX END WHILE");
			}
		}
//		System.out.println("PMX END FOR");
		
		//Completa o resto das posicoes vazias com os elementos do pai2
		for(int i=0; i < tamanho; i++) {  
			if(filho.get(i) == null) {
				filho.set(i,(Hash_Items.get(pai2_String[i])));
			}
		}
		return filho;
	}
	
	
	/*
	 * Funcao ConvertToString: Converte um pai representado por um ArrayList contendo
	 * os pesos dos itens, para um vetor de string. Cada posicao do vetor contem uma 
	 * key de um hashmap, que esta associado a um unico peso de um item. Dessa forma,
	 * garantimos que nao havera problemas com itens duplicados durante as recombinacoes
	 */
	
	public static String[] ConvertToString(HashMap<String, Integer> hash_map, List<Integer> parent) {
		
		int tamanho = hash_map.size();
		String[] parentString = new String[tamanho];
		List<Integer> posRepetidas = new ArrayList<Integer>();
		
		for (int i = 1; i <= tamanho; i++) {

			// Pega o valor correspondente a key do hashmap, começando pela primeira posicao
			Integer valor = hash_map.get("N"+i); 
			
			int indice = parent.indexOf(valor); // Busca a posicao desse valor em pai
			
			// Se este valor apareceu uma unica vez
			if (posRepetidas.contains(indice) == false) {
				parentString[indice] = ("N"+i);
				posRepetidas.add(indice);
			}

			// Se este valor apareceu mais de uma vez indexOf ira retornar 
			// um indice repetido, da primeira ocorrencia
			else {
			
				int lastPos = indice; // Guarda a posicao da primeira ocorrencia do valor repetido em pai

				List<Integer> parentCopy = new ArrayList<Integer>();

				do {
					// Copia sublista, a partir da posicao seguinte do indice guardado ate
					// parent.size() (porque eh exclusivo)
					parentCopy = parent.subList(lastPos + 1, tamanho);

					// Procura proxima ocorrendia do elemento na sublista, 
					// retorna -1 se nao encontrar
					lastPos = parentCopy.indexOf(valor);

				} while (posRepetidas.contains(indice + (lastPos + 1)) == true);

				if (lastPos != -1) {
					// Guarda posicao da nova ocorrencia, em relacao a sua posicao original na lista pai
					indice = indice + (lastPos + 1);
					posRepetidas.add(indice);
					parentString[indice] = ("N"+i);
				}
			}
		}
		
		return parentString;
	}
	
	
	/*
	 * Funcao RandomIntervalo: Gera dois valores positivos inteiros aleatoriamente,
	 * num intervalo de 0 ate tamanho determinado
	 */
	
	public void RandomIntervalo (int tamanho) {
		
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
		
		this.intervaloMin = a;
		this.intervaloMax = b;
	}
	
	
	/*
	 * Funcao ParcialmenteMapeado: Gera dois filhos, de forma analoga, a partir do
	 * metodo de Crossover Parcialmente Mapeado. Passando exatamente o mesmo intervalo
	 * em cada chamada para gerar os filhos com uma parte igual do primeiro pai
	 * e com o que "resta" do segundo pai
	 */
	
	public void ParcialmenteMapeado(List<Integer> pai1, List<Integer> pai2, HashMap<String, Integer> Hash_Items) {
		
		RandomIntervalo (pai1.size()); //Para cada Crossover cria um novo intervalo
		// System.out.println("PMX F1");
		filho1 = PMX(pai1, pai2, this.intervaloMin, this.intervaloMax, Hash_Items);
		// System.out.println("PMX F2");
		filho2 = PMX(pai2, pai1, this.intervaloMin, this.intervaloMax, Hash_Items);
	}
	
	
	public List<Integer> getF1() {
		return filho1;
	}
	
	
	public List<Integer> getF2() {
		return filho2;
	}
}