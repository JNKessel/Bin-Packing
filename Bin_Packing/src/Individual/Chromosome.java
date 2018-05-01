package Individual;
import Main.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chromosome {
	
	public int chromosome[] = new int[Main.items.size()];
	
	public Chromosome(){
		
		int i = 0;
		
		List<Integer> itemsClone = new ArrayList<Integer>(Main.items);
		
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
		
		chromosome = CrossOvers.PMX(chromPai.chromosome, chromMae.chromosome);
		
	}
	
	private void mutacao(){
		
	}
}
