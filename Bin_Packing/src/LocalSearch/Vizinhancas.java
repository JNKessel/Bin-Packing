
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
	public static ArrayList<ArrayList<Integer>> exchange1(ArrayList<ArrayList<Integer>> solucao){
		
		int itemNum, item, i;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		// FIXME: Shuffle serve para embaralhar os elementos da lista.
		// Nesse caso vc s� estaria trocando a ordem dos bins, mas mesmo assim alterando
		// de certa forma a solu��o. Fora que isso n�o est� ajudando em nada no algoritmo,
		// ent�o n�o vejo o porque de ter um shuffle aqui.
		Collections.shuffle(res);
		
		// FIXME: Iterando de i ate res.size() - 1 voc� estar� sempre excluindo o �ltimo bin.
		// Para iterar em todos os bins use i < res.size(), ou i <= res.size() - 1
		for(i=0; i<res.size()-1;i++){
			
			bin = res.get(i);
			
			// FIXME: O m�todo Random().nextInt(N) em Java seleciona num n�mero random
			// entre 0 (inclusivo) a N (exclusivo). Ent�o, se vc escolhe N = 50, ele vai
			// selecionar um n�mero de 0-49.
			// Se voce quer escolher um item random de um bin, vc tem que escolher um
			// n�mero de 0-(bin.size() - 1) mas na funcao vc poe Random().nextInt(bin.size())
			itemNum = new Random().nextInt(bin.size() - 1);
			
			item = bin.get(itemNum);
			
			for(ArrayList<Integer> bins : res){
				
				// FIXME: Igual a comparacao que o Marcelo utilizou com Strings == ou != isso
				// N�O FUNCIONA para Objetos em Java. A comparacao de Objetos em Java � feita
				// pelo m�todo equals. Ent�o temos q utilizar objeto1.equals(objeto2) para
				// comparar se os Objetos objeto1 e objeto2 s�o iguais. Para fazer a mesma
				// comparacao mas para verificar se os Objetos s�o diferentes podemos fazer
				// !objeto1.equals(objeto2)
				if(bins != bin){
					
					for(int itens : bins){
						
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
		
		while((solucao_aux.size() > 0) || (foundBin == false)) {
			Integer aleatoryBinNumber = random.nextInt(solucao_aux.size());
			ArrayList<Integer> aleatoryBin = solucao_aux.get(aleatoryBinNumber);
			solucao_aux.remove(aleatoryBin);
			
			itemsNum = random.ints(0, aleatoryBin.size()).distinct().limit(2).toArray();
			item1 = aleatoryBin.get(itemsNum[0]);
			item2 = aleatoryBin.get(itemsNum[1]);
			
			Integer itemsWeigth = item1 + item2;
			
			for(ArrayList<Integer> bin: res) {
				if(!aleatoryBin.equals(bin)) {
					Integer weightInActualBin = bin.stream().mapToInt(Integer::intValue).sum();
					
					if((weightInActualBin + itemsWeigth) <= binMaxCapacity) {
						bin.add(item1);
						bin.add(item2);
						ArrayList<Integer> aleatoryBinInSolution = res.get(res.indexOf(aleatoryBin));
						aleatoryBinInSolution.remove(Integer.valueOf(item1));
						aleatoryBinInSolution.remove(Integer.valueOf(item2));
						
						foundBin = true;
						break;
					}
				}
			}
		}
		
		return res;
	}
	
	public static ArrayList<ArrayList<Integer>> exchange2_B(ArrayList<ArrayList<Integer>> solucao){
		
		int itemsNum[];
		int binNum, item1, item2, i;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		Collections.shuffle(res);
		for(i=0; i<res.size()-1;i++){
			bin = res.get(i);
			ArrayList<Integer> temp = new ArrayList<Integer>(bin);
			
			itemsNum = new Random().ints(0, bin.size()-1).distinct().limit(2).toArray(); //Retorna 2 numeros aleatorios distintos no intervalo
			item1 = bin.get(itemsNum[0]);
			item2 = bin.get(itemsNum[1]);
			
			for(ArrayList<Integer> bins1 : res){
				
				for(ArrayList<Integer> bins2 : res){
				
					if(bins1 != bin && bins2 != bin){
						
						int bin1Storage = sum(bins1) + item1;
						int bin2Storage = sum(bins2) + item2;
							
						if(bin1Storage <= binSize && bin2Storage <= binSize){
							bin.remove(Integer.valueOf(item1));
							bin.remove(Integer.valueOf(item2));
								
							bins1.add(item1);
							bins2.add(item2);
							return res;
						}	
					}
				}
			}
		}
		return res;
	}
	
	public static ArrayList<ArrayList<Integer>> exchange2_C(ArrayList<ArrayList<Integer>> solucao){
		
		int itemsNum[];
		int binNum, item1, item2, i;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		Collections.shuffle(res);
		for(i=0; i<res.size()-1;i++){
			bin = res.get(binNum);
			ArrayList<Integer> temp = new ArrayList<Integer>(bin);
			
			itemsNum = new Random().ints(0, bin.size()-1).distinct().limit(2).toArray(); //Retorna 2 numeros aleatorios distintos no intervalo
			item1 = bin.get(itemsNum[0]);
			item2 = bin.get(itemsNum[1]);
			
			for(ArrayList<Integer> bins : res){
				
				if(bins != bin){
					
					int maxItem = Collections.max(bins);
						
					if(sum(bins) + item1 + item2 <= binSize && sum(bin) + maxItem <= binSize){
						bin.remove(Integer.valueOf(item1));
						bin.remove(Integer.valueOf(item2));	
						bins.add(item1);
						bins.add(item2);
						
						bins.remove(Integer.valueOf(maxItem));	
						bin.add(maxItem);
						
						return res;
					}	
				}	
			}
		}
		return res;
	}
	
	public static ArrayList<ArrayList<Integer>> shake(ArrayList<ArrayList<Integer>> solucao){
		
		int itemNum, item, i, item2Num, j;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		Collections.shuffle(res);
		
		for(i=0; i<res.size()-1;i++){
			
			bin = res.get(i);
			
			itemNum = new Random().nextInt(bin.size() - 1);
			
			item = bin.get(itemNum);
			
			for(ArrayList<Integer> bins2 : res){
				
				if(bins2 != bin){
					
					Collections.shuffle(bins2);
					
					for(j=0; j<bins2.size()-1;j++){
						item2Num = bins2.get(j);
						
						for(ArrayList<Integer> bins3 : res){
							if(bins3 != bins2 && bins3 != bin){
								
								if(sum(bins3) + item2Num <= binSize && sum(bins2) - item2Num + item <= binSize){
								
									bins3.add(item2Num);
									
									bins2.remove(Integer.valueOf(item2Num));
									bins2.add(item);
									
									bin.remove(Integer.valueOf(item));
									
									return res;
								}
							}
						}
					}
				}	
			}
		}
		return res;
	}
	
	private static int sum(List<Integer> list) {
	     int sum = 0; 

	     for (int i : list)
	         sum = sum + i;

	     return sum;
	}
	
}
