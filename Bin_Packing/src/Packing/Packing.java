package Packing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import LocalSearch.LocalSearch.BinsComparator;

public class Packing {

	
	public static int FirstFit_GN(List<Integer> itens, int numberOfItens, int capacidade) {

		int NumBins = 0;
		List<Integer> BinFreeSpace = new ArrayList<Integer>(Collections.nCopies(numberOfItens, capacidade)); // numberOfItens

		for (int i = 0; i < numberOfItens; i++) {

			int j;
			
			for (j = 0; j < NumBins; j++) {

				// Insere o item[i] no Bin[j], se houver espaco
				if (BinFreeSpace.get(j) >= itens.get(i)) {
					BinFreeSpace.set(j, BinFreeSpace.get(j) - itens.get(i));
					break;
				}
			}

			// Se nao houver onde inserir o item[i], acrescenta mais um bin e o insere
			if (j == NumBins) {
				NumBins++;
				BinFreeSpace.set(NumBins, BinFreeSpace.get(NumBins) - itens.get(i));
			}
		}
		
		NumBins++;
		return (NumBins);
	}

	public static int NextFit_GN(List<Integer> itens, int numberOfItens, int capacidade) {
		int NumBins = 0;
		int BinFreeSpace = capacidade;

		for (int i = 0; i < numberOfItens; i++) {

			// Se houver espaco, insere o item[i] no ultimo Bin alocado
			if (BinFreeSpace >= itens.get(i)) {
				BinFreeSpace -= itens.get(i);
			}

			// Se nao houver onde inserir o item[i], acrescenta mais um bin e o insere
			else {
				NumBins++;
				BinFreeSpace = capacidade - itens.get(i);
			}
		}
		
		NumBins++;
		return NumBins;
	}

	public static List<List<Integer>> FirstFit_LS (List<Integer> items, int binMaxCapacity) {
		List<List<Integer>> solution = new ArrayList<List<Integer>>();
		Boolean fitted;
		
		// Para cada item nos items
		for(Integer item: items) {
			// Inicializa fitted como "nao encaixou em nenhum bin"
			fitted = false;
			// Procura um bin em que o item encaixa
			for(List<Integer> bin: solution) {
				Integer totalWeight = bin.stream().mapToInt(Integer::intValue).sum();
				if(totalWeight + item <= binMaxCapacity) {
					// Ao encontrar um bin em que o item encaixa, adiciona ele ao bin
					// e muda fitted para "encaixou em algum bin"
					bin.add(item);
					fitted = true;
					break;
				}
			}
			// Se ao final da procura o item "nao encaixou em nenhum bin"
			if(fitted == false) {
				// Adiciona um novo bin e coloca o item
				List<Integer> newBin = new ArrayList<Integer>();
				newBin.add(item);
				solution.add(newBin);
			}
		}
		
		return solution;
	}
	
	public static List<List<Integer>> Realocate_LS (List<List<Integer>> solution, int binMaxCapacity) {
		List<List<Integer>> better_solution = new ArrayList<List<Integer>>(solution);
		List<List<Integer>> aux1 = new ArrayList<List<Integer>>(solution);
		List<List<Integer>> aux2 = new ArrayList<List<Integer>>(solution);
		
		// Ordena os bins por ordem crescente de peso / ordem descrescente de espaço livre
		Collections.sort(aux1, new WeightComparator());
		// Ordena os bins por ordem decrescente de peso / ordem crescente de espaço livre
		Collections.sort(aux2, new ReverseWeightComparator());
		
		// Para todos os bins começando do que tem mais espaço livre para o que tem menos espaço livre
		for(List<Integer> bin1: aux1) {
			// Para todos os itens desse bin
			for(Integer itemInBin1: bin1) {
				// Para todos os bins começando do que está mais cheio para o que está mais vazio
				for(List<Integer> bin2: aux2) {
					// Confere se nao é o mesmo bin
					if(bin1 != bin2) {
						Integer weightInBin2 = bin2.stream().mapToInt(Integer::intValue).sum();
						// Tenta encaixar o item do bin mais vazio no bin mais cheio
						if((weightInBin2 + itemInBin1) <= binMaxCapacity) {
							List<Integer> Bin1InSolution = better_solution.get(better_solution.indexOf(bin1));
							List<Integer> Bin2InSolution = better_solution.get(better_solution.indexOf(bin2));
							Bin1InSolution.remove(Integer.valueOf(itemInBin1));
							Bin2InSolution.add(itemInBin1);
							
							break;
						}
					}
				}
			}
		}
		
		return better_solution;
	}
	
	// Compara o peso dos bins (menor ganha)
	public static class WeightComparator implements Comparator<List<Integer>> {
	    @Override
	    public int compare(List<Integer> o1, List<Integer> o2) {
	    	Integer weight_o1 = o1.stream().mapToInt(Integer::intValue).sum();
	    	Integer weight_o2 = o2.stream().mapToInt(Integer::intValue).sum();
	        return weight_o1.compareTo(weight_o2);
	    }
	}

	// Compara o peso dos bins (maior ganha)
	public static class ReverseWeightComparator implements Comparator<List<Integer>> {
	    @Override
	    public int compare(List<Integer> o1, List<Integer> o2) {
	    	Integer weight_o1 = o1.stream().mapToInt(Integer::intValue).sum();
	    	Integer weight_o2 = o2.stream().mapToInt(Integer::intValue).sum();
	        return -weight_o1.compareTo(weight_o2);
	    }
	}

}