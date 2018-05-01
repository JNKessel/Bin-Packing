package Individual;

import java.util.ArrayList;

public class CrossOvers {
	
	private static Integer[] filho1;
	private static Integer[] filho2;
	
	private static Integer[] PMX (Integer pai1[], Integer pai2[]) {
		
		int tam = pai1.length;
		Integer[] filho = new Integer[tam];
		ArrayList<Integer> ValoresInseridos = new ArrayList<Integer>();
		
		int a = (int) Math.ceil((double) pai1.length/4);
		int b = (int) Math.ceil((double) (pai1.length - pai1.length/4));
		
		//Seleciona metade dos elementos do pai1 e pai2 e copia para filho1 e filho2,
		//respectivamente, em suas posicoes correspondentes, 
		for(int i=a ; i<b ; i++) {
			filho[i] = pai1[i];
			ValoresInseridos.add(pai1[i]);
		}
		
		//Procura os elementos do pai2 nas mesmas 
		//posicoes para tentar inseri-los
		for(int i=0; i<(tam/2); i++) {
			
			if(pai1[a+i] != pai2[a+i] && ValoresInseridos.contains(pai2[a+i]) == false) {
				int elemento = pai1[a+i];
				int j=0;
				
				while(j<tam) {
					//Caso a posicao do elemento do pai1 procurado, seja 
					//encontrada no vetor pai2
					if(elemento == pai2[j] ) {
						
						if (filho[j] == null) {
							//Caso a posicao esteja livre, o elemento do pai2 eh inserido
							filho[j] = pai2[a+i];
							ValoresInseridos.add(pai2[a+i]);
							break;
						}
						
						else {
							//Caso a posicao ja tenha sido preenchida
							elemento = pai1[j];
							j = 0;
						}
					}
					else
						j++;
				}
			}
		}
		
		//Completa o resto das posicoes vazias com os elementos do pai2
		for(int i=0; i < tam; i++) {  
			if(filho[i] == null)
				filho[i] = pai2[i];
			
		}
		return filho;
	}
	
	public void ParcialmenteMapeado (Integer pai1[], Integer pai2[]) {
		filho1 = PMX(pai1,pai2);
		filho2 = PMX(pai2,pai1);
	}
	
	public Integer[] getF1() {
		return filho1;
	}
	
	public Integer[] getF2() {
		return filho2;
	}
	
	/*
	 * 								Exemplo de main para teste
	 * 
	public static void main(String[] args) {
		CrossOvers c = new CrossOvers();
		Integer[] p1 = {1,2,3,4,5,6,7,8,9};
		Integer[] p2 = {9,3,7,8,2,6,5,1,4};
		
		long start = System.currentTimeMillis();
		
		c.ParcialmenteMapeado(p1, p2);
		
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.println("Total time: " + elapsedTime / 1000.0 + "s");
		
		Exibe(c.getF1());
		Exibe(c.getF2());
	}
	
	public static void Exibe(Integer[] vi) {
		
		for(int i=0; i<vi.length; i++) {
			System.out.print(vi[i]+", ");
			
		}
		System.out.println("\n");
		
	}
	*/
	
}