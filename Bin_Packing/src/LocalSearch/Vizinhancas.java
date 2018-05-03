
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Vizinhancas {
	
	public static ArrayList<ArrayList<Integer>> exchange1(ArrayList<ArrayList<Integer>> solucao){
		
		int itemNum, item, i;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		Collections.shuffle(res);
		
		for(i=0; i<res.size();i++){
			
			bin = res.get(i);
			
			itemNum = new Random().nextInt(bin.size() - 1);
			
			item = bin.get(itemNum);
			
			for(ArrayList<Integer> bins : res){
				
				if(bins != bin){
					
					for(int itens : bins){
						
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
	
	public static ArrayList<ArrayList<Integer>> exchange2_A(ArrayList<ArrayList<Integer>> solucao){
		
		int itemsNum[];
		int binNum, item1, item2, i;
		ArrayList<Integer> bin;
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>(solucao);
		
		Collections.shuffle(res);
		for(i=0; i<res.size();i++){
			bin = res.get(i);
			ArrayList<Integer> temp = new ArrayList<Integer>(bin);
			
			itemsNum = new Random().ints(0, bin.size()-1).distinct().limit(2).toArray(); //Retorna 2 numeros aleatorios distintos no intervalo
			item1 = bin.get(itemsNum[0]);
			item2 = bin.get(itemsNum[1]);
			
			for(ArrayList<Integer> bins : res){
				
				if(bins != bin){
						
					if(sum(bins) + item1 + item2 <= binSize){
						bin.remove(Integer.valueOf(item1));
						bin.remove(Integer.valueOf(item2));
							
						bins.add(item1);
						bins.add(item2);
						return res;
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
		for(i=0; i<res.size();i++){
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
		for(i=0; i<res.size();i++){
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
	
	private static int sum(List<Integer> list) {
	     int sum = 0; 

	     for (int i : list)
	         sum = sum + i;

	     return sum;
	}
}
