
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Vizinhancas {
	
	// FIXME: O algoritmo está implementado errado. Você so esta procurando para 1 item de
	// 1 bin, ele troca com qualquer outro de outro bin. Mas se nao acontecer essa troca,
	// voce tem q escolher outro bin aleatorio e testar todas possibilidades novamente.
	// PORQUE? Vou dar um exemplo, supondo capacidade 10:
	// [ [10] ,  [1, 9] , [1, 7] ]
	// Se eu escolher o bin 1 [10], nenhum dos itens dele vai conseguir trocar com nenhum
	// item dos outros bins, mas eu poderia trocar o 7 com o 9 que daria certo
	// Por isso temos q voltar a tentar selecionar outro bin e testar todas as possibilidades
	public static ArrayList<ArrayList<Integer>> exchange1(ArrayList<ArrayList<Integer>> solucao){
		
		int itemNum, item, i;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		// FIXME: Shuffle serve para embaralhar os elementos da lista.
		// Nesse caso vc só estaria trocando a ordem dos bins, mas mesmo assim alterando
		// de certa forma a solução. Fora que isso não está ajudando em nada no algoritmo,
		// então não vejo o porque de ter um shuffle aqui.
		Collections.shuffle(res);
		
		// FIXME: Iterando de i ate res.size() - 1 você estará sempre excluindo o último bin.
		// Para iterar em todos os bins use i < res.size(), ou i <= res.size() - 1
		for(i=0; i<res.size()-1;i++){
			
			bin = res.get(i);
			
			// FIXME: O método Random().nextInt(N) em Java seleciona num número random
			// entre 0 (inclusivo) a N (exclusivo). Então, se vc escolhe N = 50, ele vai
			// selecionar um número de 0-49.
			// Se voce quer escolher um item random de um bin, vc tem que escolher um
			// número de 0-(bin.size() - 1) mas na funcao vc poe Random().nextInt(bin.size())
			itemNum = new Random().nextInt(bin.size() - 1);
			
			item = bin.get(itemNum);
			
			for(ArrayList<Integer> bins : res){
				
				// FIXME: Igual a comparacao que o Marcelo utilizou com Strings == ou != isso
				// NÃO FUNCIONA para Objetos em Java. A comparacao de Objetos em Java é feita
				// pelo método equals. Então temos q utilizar objeto1.equals(objeto2) para
				// comparar se os Objetos objeto1 e objeto2 são iguais. Para fazer a mesma
				// comparacao mas para verificar se os Objetos são diferentes podemos fazer
				// !objeto1.equals(objeto2)
				if(bins != bin){
					
					for(int itens : bins){
						
						// Vi que voce fez uma funcao para somar os itens em um bin. Não tem
						// o menor problema fazer isso, mas se voce tivesse pesquisado, ja
						// existe em Java uma função pronta para fazer isso.
						// Para List<Integer> minha_lista:
						// int sum = minha_lista.stream().mapToInt(Integer::intValue).sum();

						// FIXME: Não é bom ter uma função de some, mesmo q seja da biblioteca
						// de Java ou outra coisa sendo refeita 2 vezes em um NESTED FOR.
						// Isso vai custar mt tempo. Coloca uma variável fora do for pra 
						// guardar o valor desse (sum(bin) - item) que vc vai usar sempre aqui
						
						// FIXME: Só por garantia, coloca parenteses nas condicoes (A) && (B)
						
						// FIXME: Esse sum(bins) também pode ficar numa variável fora deste for
						// pra evitar o custo de refazer a operacao
						
						// FIXME: Se a variavel binSize for a capacidade máxima de um bin, coloca
						// um parametro na funcao que eu passo essa variavel pra voce.
						
						// Não precisa desse Integer.valueOf(item) na remocção de um item do bin,
						// mas nao tem problema
						
						// FIXME: Assim que você acha um bin para trocar os itens, é bom dar um break
						// para parar o for e evitar o custo de ficar reiterando o for e uma outra
						// condicao para dar break no outro for também. Isso é bom tanto para
						// evitar que o programa continua procurando o bin quando voce ja achou ele
						// e para evitar que exista outro bin que atenda aos mesmos requisitos
						// e ai ele vai trocar de novo ate que os fors acabem.
						// OBS: Funções em Java acho q podem retornar mais de uma vez, ele nao
						// para quando da return igual a liguagem C.
						if(sum(bin) - item + itens <= binSize && sum(bins) - itens + item <= binSize){
							bin.remove(Integer.valueOf(item));
							bin.add(itens);
							
							bins.remove(Integer.valueOf(itens));
							bins.add(item);	
							return res;
						}
					}
				}	
			}
		}
		return res;
	}
	
	public static ArrayList<ArrayList<Integer>> exchange2_A(ArrayList<ArrayList<Integer>> solucao, Integer binMaxCapacity){
		
		int itemsNum[];
		int binNum, item1, item2, i;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		ArrayList<ArrayList<Integer>> solucao_aux = new ArrayList<ArrayList<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		
		// Até conseguir encaixar os itens aleatórios A e B de um bin aleatório C em
		// um bin D ou até acabarem os bins sorteados
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			// Escolhe um bin aleatório C
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			ArrayList<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			ArrayList<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));
			
			// Escolhe dois itens aleatórios A e B do bin C
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
			
			Integer itemsWeigth = item1 + item2;
			
			// Para todos os outros bins
			for(ArrayList<Integer> bin: res) {
				if(aleatoryBinInSolution != bin) {
					Integer weightInActualBin = bin.stream().mapToInt(Integer::intValue).sum();
					
					// Tenta encaixar os itens A e B no bin D
					if((weightInActualBin + itemsWeigth) <= binMaxCapacity) {
						bin.add(item1);
						bin.add(item2);
						aleatoryBinInSolution.remove(Integer.valueOf(item1));
						aleatoryBinInSolution.remove(Integer.valueOf(item2));
						
						foundBin = true;
						break;
					}
				}
			}
		}
		
		if(foundBin) {
			return res;
		} else {
			return null;
		}
	}
	
	public static ArrayList<ArrayList<Integer>> exchange2_B(ArrayList<ArrayList<Integer>> solucao, Integer binMaxCapacity){
		int itemsNum[];
		int binNum, item1, item2, i;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		ArrayList<ArrayList<Integer>> solucao_aux = new ArrayList<ArrayList<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		
		// Até conseguir trocar os itens aleatórios A e B de um bin aleatório C para
		// um bin D ou um item para um bin D e o outro item para um bin E ou acabarem
		// os bins sorteados
		// OBS: Não testa todas as possibilidades devido ao sorteio dos itens no bin
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			// Escolhe um bin aleatório C
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			ArrayList<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			ArrayList<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));

			// Escolhe dois itens aleatórios A e B desse bin C
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
						
			searchForBins: {
				// Para todos os outros bins
				for(ArrayList<Integer> bin1: res) {	
					if((aleatoryBinInSolution != bin1)) {
						Integer weightInActualBin1 = bin1.stream().mapToInt(Integer::intValue).sum();
						
						// Tenta encaixar os itens A e B em um bin D
						if((weightInActualBin1 + item1 + item2) <= binMaxCapacity) {
							bin1.add(item1);
							bin1.add(item2);
							aleatoryBinInSolution.remove(Integer.valueOf(item1));
							aleatoryBinInSolution.remove(Integer.valueOf(item2));
							
							foundBin = true;
							break searchForBins;
						}
						
						List<ArrayList<Integer>> others = res.subList(res.indexOf(bin1) + 1, res.size());
						
						// Para todos os outros bins a partir do bin atual
						for(ArrayList<Integer> bin2: others) {
							if(aleatoryBinInSolution != bin2) {
								Integer weightInActualBin2 = bin2.stream().mapToInt(Integer::intValue).sum();
								
								// Tenta encaixar um item em um bin D e o outro item em um bin E
								if((weightInActualBin1 + item1 <= binMaxCapacity) &&
									(weightInActualBin2 + item2 <= binMaxCapacity)) {
									bin1.add(Integer.valueOf(item1));
									bin2.add(Integer.valueOf(item2));
									aleatoryBinInSolution.remove(Integer.valueOf(item1));
									aleatoryBinInSolution.remove(Integer.valueOf(item2));
									
									foundBin = true;
									break searchForBins;
								} else if((weightInActualBin1 + item2 <= binMaxCapacity) &&
										(weightInActualBin2 + item1 <= binMaxCapacity)) {
									bin1.add(Integer.valueOf(item2));
									bin2.add(Integer.valueOf(item1));
									aleatoryBinInSolution.remove(Integer.valueOf(item1));
									aleatoryBinInSolution.remove(Integer.valueOf(item2));
									
									foundBin = true;
									break searchForBins;
								}
							}
						}
					}
				}
			}
		}
		
		if(foundBin) {
			return res;
		} else {
			return null;
		}
	}
	
	public static ArrayList<ArrayList<Integer>> exchange2_C(ArrayList<ArrayList<Integer>> solucao, Integer binMaxCapacity){
		int itemsNum[];
		int binNum, item1, item2, i;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);		
		ArrayList<ArrayList<Integer>> solucao_aux = new ArrayList<ArrayList<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		
		// Até conseguir encaixar dois itens aleatórios A e B de um bin aleatório C em um outro
		// bin D, e um item aleatório E desse bin D no bin C ou até acabarem os bins sorteados
		// OBS: Não testa todas as possibilidades devido ao sorteio dos itens nos bins
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			// Escolhe um bin aleatório C
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			ArrayList<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			ArrayList<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));
			
			Integer weightInAleatoryBin = aleatoryBin.stream().mapToInt(Integer::intValue).sum();
			
			// Escolhe 2 itens aleatórios A e B desse bin C
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
			
			Integer itemsWeigth = item1 + item2;
			
			// Para todos os outros bins
			for(ArrayList<Integer> bin: res) {
				if(aleatoryBinInSolution != bin) {
					// Escolhe um item aleatório E desse bin D
					Integer aleatoryItemNumber = random.nextInt(bin.size());
					Integer aleatoryItem = bin.get(aleatoryItemNumber);
					Integer weightInActualBin = bin.stream().mapToInt(Integer::intValue).sum();
					
					// Tenta encaixar os itens A e B no bin D e o item E no bin C
					if(((weightInActualBin - aleatoryItem + itemsWeigth) <= binMaxCapacity) &&
						(weightInAleatoryBin - itemsWeigth + aleatoryItem <= binMaxCapacity)) {
						bin.add(item1);
						bin.add(item2);
						bin.remove(aleatoryItemNumber);
						aleatoryBinInSolution.add(aleatoryItem);
						aleatoryBinInSolution.remove(Integer.valueOf(item1));
						aleatoryBinInSolution.remove(Integer.valueOf(item2));
						
						foundBin = true;
						break;
					}
				}
			}
		}
		
		if(foundBin) {
			return res;
		} else {
			return null;
		}
	}
	
	public static ArrayList<ArrayList<Integer>> shake(ArrayList<ArrayList<Integer>> solucao, Integer binMaxCapacity){
		int itemNum, item, i, item2Num, j;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		Integer aleatoryItemNumber;
		
		// Percorre todas as combinações possiveis N bins 3 a 3, mas não testa
		// para todos os itens de todos os bins
		searchForBins: {
			// Para todos os bins do primeiro ao antipenúltimo da lista
			for(ArrayList<Integer> bin1: res.subList(0, res.size() - 2)) {
				// Escolhe um item aleatório
				aleatoryItemNumber = random.nextInt(bin1.size());
				Integer aleatoryItemBin1 = bin1.get(aleatoryItemNumber);
				Integer weightInActualBin1 = bin1.stream().mapToInt(Integer::intValue).sum();
												
				// Para todos os bins do bin seguinte ao bin1 até o penúltimo
				for(ArrayList<Integer> bin2: res.subList(res.indexOf(bin1) + 1, res.size() - 1)) {
					// Escolhe um item aleatório
					aleatoryItemNumber = random.nextInt(bin2.size());
					Integer aleatoryItemBin2 = bin2.get(aleatoryItemNumber);
					Integer weightInActualBin2 = bin2.stream().mapToInt(Integer::intValue).sum();
												
					// Para todos os bins do bin seguinte ao bin2 até o último
					for(ArrayList<Integer> bin3: res.subList(res.indexOf(bin2) + 1, res.size())) {
						Integer weightInActualBin3 = bin3.stream().mapToInt(Integer::intValue).sum();
						
						// Tenta simultâneamente colocar o item sorteado do bin2 no bin3
						// e o item sorteado do bin1 no bin2
						if(((weightInActualBin3 + aleatoryItemBin2) <= binMaxCapacity) &&
							((weightInActualBin2 - aleatoryItemBin2 + aleatoryItemBin1) <= binMaxCapacity)) {
							bin1.remove(Integer.valueOf(aleatoryItemBin1));
							bin2.add(aleatoryItemBin1);
							bin2.remove(Integer.valueOf(aleatoryItemBin2));
							bin3.add(aleatoryItemBin2);
							
							foundBin = true;
							break searchForBins;
						}
					}
					
				}
			}
		}
		
		if(foundBin) {
			return res;
		} else {
			return null;
		}
	}
	
	public static ArrayList<ArrayList<Integer>> restructure(ArrayList<ArrayList<Integer>> solucao, Integer binMaxCapacity){
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		Random random = new Random();
		ArrayList<Integer> binMoreFreeSpace;
		Integer weightBinMoreFreeSpace = Integer.MAX_VALUE;
		ArrayList<Integer> remainingItems = new ArrayList<Integer>();
		Integer maxTries = 100;
		Integer tries = 0;
		
		// Encontrar o bin com mais espaço vazio
		for(ArrayList<Integer> bin: res) {
			Integer weightInActualBin = bin.stream().mapToInt(Integer::intValue).sum();
			if(weightInActualBin < weightBinMoreFreeSpace) {
				weightBinMoreFreeSpace = weightInActualBin;
				binMoreFreeSpace = bin;
			}
		}
		
		// Remover todos os itens do bin com mais espaço vazio
		for(Integer item: binMoreFreeSpace) {
			remainingItems.add(item);	
		}
		binMoreFreeSpace.clear();
		
		// Remover 20% dos itens de todos os outros bins
		// (arrendondando para baixo caso o número de itens seja quebrado)
		for(ArrayList<Integer> bin: res) {
			if(bin != binMoreFreeSpace) {
				Double aux = Math.floor(Double.valueOf(bin.size())*20/100);
				Integer numberOrItemsToRemove = aux.intValue();
				
				for(int i = 0; i < numberOrItemsToRemove; i++) {
					remainingItems.add(bin.get(i));
					bin.remove(i);
				}
			}
		}
		
		// Até encontrar uma configuracao encaixando nos bins todos os itens retirados
		// Ou estourar o número de iteracoes
		while((remainingItems.size() > 0) && (tries < maxTries)) {
			tries += 1;
			Collections.shuffle(remainingItems);
			for(Integer item: remainingItems) {
				for(ArrayList<Integer> bin: res) {
					Integer weightInActualBin = bin.stream().mapToInt(Integer::intValue).sum();
					if((weightInActualBin + item) <= binMaxCapacity) {
						bin.add(item);
						remainingItems.remove(Integer.valueOf(item));
						break;
					}
				}
			}
		}
		
		if(tries == maxTries || remainingItems.size() > 0) {
			return null;
		} else {
			return res;
		}
	}
	
	private static int sum(List<Integer> list) {
	     int sum = 0; 

	     for (int i : list)
	         sum = sum + i;

	     return sum;
	}
	
}
