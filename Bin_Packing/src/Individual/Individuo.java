package Individual;

import java.util.List;

public class Individuo {
	
	private Chromosome chromosome;
	private int fitness;
	
	public Individuo(List<Integer> genes){
		
		chromosome = new Chromosome();
		
	}
	
	public Individuo(Individuo pai, Individuo mae){
		
		chromosome = new Chromosome(pai.chromosome, mae.chromosome);
		
	}
	
	 public int getFitness(){
		 return fitness;
	 }

}
