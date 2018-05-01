package Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Main.Main;

public class Chromosome {
	
	private int chromosome[] = new int[Main.items.size()];
	
	public Chromosome(){
		
		int i = 0;
		
		ArrayList<String> itemsClone = (ArrayList<String>) (((ArrayList<String>) Main.items).clone());
		
		Collections.shuffle(itemsClone);
		
		for(int item: itemsClone){
			chromosome[i] = item;
		    i++;
		}	
	}
	
	public Chromosome(Chromosome chromPai, Chromosome chromMae){
		
		recombinacao(chromPai, chromMae);
		mutacao();
		
	}
	
	private void recombinacao(Chromosome chromPai, Chromosome chromMae){
		
	}
	
	private void mutacao(){
		
	}
}
