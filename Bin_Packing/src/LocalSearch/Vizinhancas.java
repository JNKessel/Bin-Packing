package LocalSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Vizinhancas {
	
	// FIXME: O algoritmo est� implementado errado. Voc� so esta procurando para 1 item de
	// 1 bin, ele troca com qualquer outro de outro bin. Mas se nao acontecer essa troca,
	// voce tem q escolher outro bin aleatorio e testar todas possibilidades novamente.
	// PORQUE? Vou dar um exemplo, supondo capacidade 10:
	// [ [10] ,  [1, 9] , [1, 7] ]
	// Se eu escolher o bin 1 [10], nenhum dos itens dele vai conseguir trocar com nenhum
	// item dos outros bins, mas eu poderia trocar o 7 com o 9 que daria certo
	// Por isso temos q voltar a tentar selecionar outro bin e testar todas as possibilidades
	public static List<List<Integer>> exchange1(List<List<Integer>> solucao, Integer binMaxCapacity){
		
		int itemNum, item, i, somaBin1, somaBin2, bin1Num;
		List<Integer> bin1;
		List<List<Integer>> res = new ArrayList<List<Integer>>(solucao);
		
		List<List<Integer>> temp = res;
		for(i=0; i<res.size();i++){
			
			bin1Num = new Random().nextInt(temp.size());
			bin1 = temp.get(i);
			temp.remove(bin1Num);
			
			somaBin1 = sum(bin1);
			
			// FIXME: O m�todo Random().nextInt(N) em Java seleciona num n�mero random
			// entre 0 (inclusivo) a N (exclusivo). Ent�o, se vc escolhe N = 50, ele vai
			// selecionar um n�mero de 0-49.
			// Se voce quer escolher um item random de um bin, vc tem que escolher um
			// n�mero de 0-(bin.size() - 1) mas na funcao vc poe Random().nextInt(bin.size())
			itemNum = new Random().nextInt(bin1.size() - 1);
			
			item = bin1.get(itemNum);
			
			for(List<Integer> bin2 : res){
				
				somaBin2 = sum(bin2);
				
				// FIXME: Igual a comparacao que o Marcelo utilizou com Strings == ou != isso
				// N�O FUNCIONA para Objetos em Java. A comparacao de Objetos em Java � feita
				// pelo m�todo equals. Ent�o temos q utilizar objeto1.equals(objeto2) para
				// comparar se os Objetos objeto1 e objeto2 s�o iguais. Para fazer a mesma
				// comparacao mas para verificar se os Objetos s�o diferentes podemos fazer
				// !objeto1.equals(objeto2)
				if(bin2 != bin1){
					
					for(int itens : bin2){
						
						// Vi que voce fez uma funcao para somar os itens em um bin. N�o tem
						// o menor problema fazer isso, mas se voce tivesse pesquisado, ja
						// existe em Java uma fun��o pronta para fazer isso.
						// Para List<Integer> minha_lista:
						// int sum = minha_lista.stream().mapToInt(Integer::intValue).sum();

						// FIXME: N�o � bom ter uma fun��o de some, mesmo q seja da biblioteca
						// de Java ou outra coisa sendo refeita 2 vezes em um NESTED FOR.
						// Isso vai custar mt tempo. Coloca uma vari�vel fora do for pra 
						// guardar o valor desse (sum(bin) - item) que vc vai usar sempre aqui
						
						// FIXME: S� por garantia, coloca parenteses nas condicoes (A) && (B)
						
						// FIXME: Esse sum(bins) tamb�m pode ficar numa vari�vel fora deste for
						// pra evitar o custo de refazer a operacao
						
						// FIXME: Se a variavel binSize for a capacidade m�xima de um bin, coloca
						// um parametro na funcao que eu passo essa variavel pra voce.
						
						// N�o precisa desse Integer.valueOf(item) na remoc��o de um item do bin,
						// mas nao tem problema
						
						// FIXME: Assim que voc� acha um bin para trocar os itens, � bom dar um break
						// para parar o for e evitar o custo de ficar reiterando o for e uma outra
						// condicao para dar break no outro for tamb�m. Isso � bom tanto para
						// evitar que o programa continua procurando o bin quando voce ja achou ele
						// e para evitar que exista outro bin que atenda aos mesmos requisitos
						// e ai ele vai trocar de novo ate que os fors acabem.
						// OBS: Fun��es em Java acho q podem retornar mais de uma vez, ele nao
						// para quando da return igual a liguagem C.
						if(somaBin1 - item + itens <= binMaxCapacity && somaBin2 - itens + item <= binMaxCapacity){
							bin1.remove(Integer.valueOf(item));
							bin1.add(itens);
							
							bin2.remove(Integer.valueOf(itens));
							bin2.add(item);	
							return res;
						}
					}
				}	
			}
		}
		return res;
	}
	
	public static List<List<Integer>> exchange2_A(List<List<Integer>> solucao, Integer binMaxCapacity){
		
		int itemsNum[];
		int binNum, item1, item2, i;
		List<List<Integer>> res = new ArrayList<List<Integer>>(solucao);
		List<List<Integer>> solucao_aux = new ArrayList<List<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		
		// At� conseguir encaixar os itens aleat�rios A e B de um bin aleat�rio C em
		// um bin D ou at� acabarem os bins sorteados
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			// Escolhe um bin aleat�rio C
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			List<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			List<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));
			
			// Escolhe dois itens aleat�rios A e B do bin C
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
			
			Integer itemsWeigth = item1 + item2;
			
			// Para todos os outros bins
			for(List<Integer> bin: res) {
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
	
	public static List<List<Integer>> exchange2_B(List<List<Integer>> solucao, Integer binMaxCapacity){
		int itemsNum[];
		int binNum, item1, item2, i;
		List<List<Integer>> res = new ArrayList<List<Integer>>(solucao);
		
		List<List<Integer>> solucao_aux = new ArrayList<List<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		
		// At� conseguir trocar os itens aleat�rios A e B de um bin aleat�rio C para
		// um bin D ou um item para um bin D e o outro item para um bin E ou acabarem
		// os bins sorteados
		// OBS: N�o testa todas as possibilidades devido ao sorteio dos itens no bin
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			// Escolhe um bin aleat�rio C
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			List<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			List<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));

			// Escolhe dois itens aleat�rios A e B desse bin C
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
						
			searchForBins: {
				// Para todos os outros bins
				for(List<Integer> bin1: res) {	
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
						
						List<List<Integer>> others = res.subList(res.indexOf(bin1) + 1, res.size());
						
						// Para todos os outros bins a partir do bin atual
						for(List<Integer> bin2: others) {
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
	
	
	public static List<List<Integer>> exchange2_C(List<List<Integer>> solucao, Integer binMaxCapacity){
		int itemsNum[];
		int binNum, item1, item2, i;
		List<List<Integer>> res = new ArrayList<List<Integer>>(solucao);		
		List<List<Integer>> solucao_aux = new ArrayList<List<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		
		// At� conseguir encaixar dois itens aleat�rios A e B de um bin aleat�rio C em um outro
		// bin D, e um item aleat�rio E desse bin D no bin C ou at� acabarem os bins sorteados
		// OBS: N�o testa todas as possibilidades devido ao sorteio dos itens nos bins
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			// Escolhe um bin aleat�rio C
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			List<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			List<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));
			
			Integer weightInAleatoryBin = aleatoryBin.stream().mapToInt(Integer::intValue).sum();
			
			// Escolhe 2 itens aleat�rios A e B desse bin C
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
			
			Integer itemsWeigth = item1 + item2;
			
			// Para todos os outros bins
			for(List<Integer> bin: res) {
				if(aleatoryBinInSolution != bin) {
					// Escolhe um item aleat�rio E desse bin D
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
	
	public static List<List<Integer>> shake(List<List<Integer>> solucao, Integer binMaxCapacity){
		int itemNum, item, i, item2Num, j;
		List<List<Integer>> res = new ArrayList<List<Integer>>(solucao);
		Boolean foundBin = false;
		Random random = new Random();
		Integer aleatoryItemNumber;
		
		// Percorre todas as combina��es possiveis N bins 3 a 3, mas n�o testa
		// para todos os itens de todos os bins
		searchForBins: {
			// Para todos os bins do primeiro ao antipen�ltimo da lista
			for(List<Integer> bin1: res.subList(0, res.size() - 2)) {
				// Escolhe um item aleat�rio
				aleatoryItemNumber = random.nextInt(bin1.size());
				Integer aleatoryItemBin1 = bin1.get(aleatoryItemNumber);
				Integer weightInActualBin1 = bin1.stream().mapToInt(Integer::intValue).sum();
												
				// Para todos os bins do bin seguinte ao bin1 at� o pen�ltimo
				for(List<Integer> bin2: res.subList(res.indexOf(bin1) + 1, res.size() - 1)) {
					// Escolhe um item aleat�rio
					aleatoryItemNumber = random.nextInt(bin2.size());
					Integer aleatoryItemBin2 = bin2.get(aleatoryItemNumber);
					Integer weightInActualBin2 = bin2.stream().mapToInt(Integer::intValue).sum();
												
					// Para todos os bins do bin seguinte ao bin2 at� o �ltimo
					for(List<Integer> bin3: res.subList(res.indexOf(bin2) + 1, res.size())) {
						Integer weightInActualBin3 = bin3.stream().mapToInt(Integer::intValue).sum();
						
						// Tenta simult�neamente colocar o item sorteado do bin2 no bin3
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
	
	public static List<List<Integer>> restructure(List<List<Integer>> solucao, Integer binMaxCapacity){
		List<List<Integer>> res = new ArrayList<List<Integer>>(solucao);
		Random random = new Random();
		List<Integer> binMoreFreeSpace = new ArrayList<Integer>();;
		Integer weightBinMoreFreeSpace = Integer.MAX_VALUE;
		List<Integer> remainingItems = new ArrayList<Integer>();
		Integer maxTries = 100;
		Integer tries = 0;
		
		// Encontrar o bin com mais espa�o vazio
		for(List<Integer> bin: res) {
			Integer weightInActualBin = bin.stream().mapToInt(Integer::intValue).sum();
			if(weightInActualBin < weightBinMoreFreeSpace) {
				weightBinMoreFreeSpace = weightInActualBin;
				binMoreFreeSpace = bin;
			}
		}
		
		// Remover todos os itens do bin com mais espa�o vazio
		for(Integer item: binMoreFreeSpace) {
			remainingItems.add(item);	
		}
		binMoreFreeSpace.clear();
		
		// Remover 20% dos itens de todos os outros bins
		// (arrendondando para baixo caso o n�mero de itens seja quebrado)
		for(List<Integer> bin: res) {
			if(bin != binMoreFreeSpace) {
				Double aux = Math.floor(Double.valueOf(bin.size())*20/100);
				Integer numberOrItemsToRemove = aux.intValue();
				
				for(int i = 0; i < numberOrItemsToRemove; i++) {
					remainingItems.add(bin.get(i));
					bin.remove(i);
				}
			}
		}
		
		// At� encontrar uma configuracao encaixando nos bins todos os itens retirados
		// Ou estourar o n�mero de iteracoes
		while((remainingItems.size() > 0) && (tries < maxTries)) {
			tries += 1;
			Collections.shuffle(remainingItems);
			for(Integer item: remainingItems) {
				for(List<Integer> bin: res) {
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
