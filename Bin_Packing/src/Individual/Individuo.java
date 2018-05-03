package Individual;

import java.util.ArrayList;
import java.util.List;

public class Individuo {
	
	private Chromosome chromosome;
	private static Integer fitness;
	private static Boolean aprovado;
	
	public Individuo(List<Integer> genes){
		chromosome = new Chromosome(genes);
	}
	
	public Individuo(Chromosome chrom){
		chromosome = chrom;
	}

	public List<Individuo> generateChild (Individuo mae) {
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
	
	public void setFitness(Integer fitness) {
		Individuo.fitness = fitness;
	}

	public Integer getFitness() {
		return Individuo.fitness;
	}

	public void setStatus(Boolean status) {
		Individuo.aprovado = status;
	}

	public Boolean getStatus() {
		return Individuo.aprovado;
	}

	public List<Integer> mutation (int type) {
		return chromosome.sufferMutation(type);
	}
	
}
