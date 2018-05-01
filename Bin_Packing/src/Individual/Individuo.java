package Individual;

import java.util.ArrayList;
import java.util.List;

public class Individuo {
	
	private Chromosome chromosome;
	
	public Individuo(List<Integer> genes){
		chromosome = new Chromosome(genes);
	}
	
	public Individuo(Chromosome chrom){
		chromosome = chrom;
	}

	public List<Individuo> gerarFilhos (Individuo mae) {
		List<Chromosome> newChromosomes = chromosome.recombineChromossomes(mae.chromosome);
		List<Individuo> newIndividuals = new ArrayList<Individuo>();
		for (Chromosome chrom: newChromosomes) {
			newIndividuals.add(new Individuo(chrom));
		}
		return newIndividuals;
	}
	
	public List<Integer> getChromosome () {
		return chromosome.getChromossomeList();
	}
	
	public List<Integer> mutation (int type) {
		return chromosome.sufferMutation(type);
	}
	
}
