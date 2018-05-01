package Individual;

import java.util.ArrayList;
import java.util.List;

public class CrossOvers {
	
	private static List<Integer> filho1;
	private static List<Integer> filho2;
	
	private static List<Integer> PMX (List<Integer> pai1, List<Integer> pai2) {
		
		int tam = pai1.size();
		List<Integer> filho = new ArrayList<Integer>(); // Filho tem o mesmo tamanho do pai
		for(int i = 0; i < tam; i++) { // Inicializar ArrayList com null (pra poder acessar as posicoes)
			filho.add(null);
		}
		
		ArrayList<Integer> ValoresInseridos = new ArrayList<Integer>();
		
		int a = (int) Math.ceil((double) pai1.size()/4);
		int b = (int) Math.ceil((double) (pai1.size() - pai1.size()/4));
		
		//Seleciona metade dos elementos do pai1 e pai2 e copia para filho1 e filho2,
		//respectivamente, em suas posicoes correspondentes, 
		for(int i=a ; i<b ; i++) {
			filho.set(i, pai1.get(i));
			ValoresInseridos.add(pai1.get(i));
		}
		
		//Procura os elementos do pai2 nas mesmas 
		//posicoes para tentar inseri-los
		for(int i=0; i<(tam/2); i++) {
			
			if(pai1.get(a+i) != pai2.get(a+i) && ValoresInseridos.contains(pai2.get(a+i)) == false) {
				int elemento = pai1.get(a+i);
				int j=0;
				
				while(j<tam) {
					//Caso a posicao do elemento do pai1 procurado, seja 
					//encontrada no vetor pai2
					if(elemento == pai2.get(j) ) {
						
						if (filho.get(j) == null) {
							//Caso a posicao esteja livre, o elemento do pai2 eh inserido
							filho.set(j, pai2.get(a+i));
							ValoresInseridos.add(pai2.get(a+i));
							break;
						}
						
						else {
							//Caso a posicao ja tenha sido preenchida
							elemento = pai1.get(j);
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
			if(filho.get(i) == null)
				filho.set(i, pai2.get(i));

		}
		return filho;
	}
	
	public void ParcialmenteMapeado (List<Integer> pai1, List<Integer> pai2) {
		filho1 = PMX(pai1,pai2);
		filho2 = PMX(pai2,pai1);
	}
	
	public List<Integer> getF1() {
		return filho1;
	}
	
	public List<Integer> getF2() {
		return filho2;
	}
	
	/*
	 * 								Exemplo de main para teste
	 * 
	public static void main(String[] args) {
		CrossOvers c = new CrossOvers();
		List<Integer> p1 = new ArrayList<Integer>();
		for (int i = 1; i < 10; i++) {
			p1.add(i);
		}
		List<Integer> p2 = new ArrayList<Integer>();
		p2.add(9); p2.add(3); p2.add(7); p2.add(8); p2.add(2); p2.add(6); p2.add(5); p2.add(1); p2.add(4);
		
		long start = System.currentTimeMillis();
		
		c.ParcialmenteMapeado(p1, p2);
		
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.println("Total time: " + elapsedTime / 1000.0 + "s");
		
		Exibe(c.getF1());
		Exibe(c.getF2());
	}
	
	public static void Exibe(List<Integer> vi) {
		
		for(int i=0; i<vi.size(); i++) {
			System.out.print(vi.get(i)+", ");
			
		}
		System.out.println("\n");
		
	}
	*/
	
}