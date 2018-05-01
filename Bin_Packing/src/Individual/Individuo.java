package Individual;

import java.util.ArrayList;
import java.util.List;

public class Individuo {
	
	private Chromosome chromosome;
	private int fitness;
	
	public Individuo(List<Integer> genes){
		chromosome = new Chromosome(genes);
	}
	
	public Individuo(Chromosome chrom){
		chromosome = chrom;
	}

	
	public List<Individuo> Individuo(Individuo pai, Individuo mae) {
		List<Chromosome> newChromosomes = pai.chromosome.recombineChromossomes(mae.chromosome);
		List<Individuo> newIndividuals = new ArrayList<Individuo>();
		for (Chromosome chrom: newChromosomes) {
			newIndividuals.add(new Individuo(chrom));
		}
		return newIndividuals;
	}
	
	 public int getFitness(){
		 return fitness;
	 }

}
