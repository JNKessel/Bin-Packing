package Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individuo {
	
	private Chromosome chromosome;
	private Integer fitness;
	private Boolean aprovado;
	
	public Individuo(List<Integer> genes){
		chromosome = new Chromosome(genes);
	}
	
	public Individuo(Chromosome chrom){
		chromosome = chrom;
	}

	public List<Individuo> generateChild (Individuo mae, Double mutationProbability, Integer type) {
		List<Chromosome> newChromosomes = chromosome.recombineChromossomes(mae.chromosome);
		List<Individuo> newIndividuals = new ArrayList<Individuo>();
		for (Chromosome chrom: newChromosomes) {
			if(sortMutation(mutationProbability)) {
				chrom.sufferMutation(type);
			}
			newIndividuals.add(new Individuo(chrom));
		}
		return newIndividuals;
	}
	
	public List<Integer> getChromosome () {
		return chromosome.getChromossomeList();
	}
	
	public void setFitness(Integer fitness) {
		this.fitness = fitness;
	}

	public Integer getFitness() {
		return this.fitness;
	}

	public void setStatus(Boolean status) {
		this.aprovado = status;
	}

	public Boolean getStatus() {
		return this.aprovado;
	}
	
	// Determinar se a mutacao deve ou nao acontece baseado na mutationProbability (0.0 to 1.0)
	private Boolean sortMutation (Double mutationProbability) {
		// Fazer uma lista com 0 para os erros e 1 para os acertos
		// A quantidade de 1's deve ser colocada com base na probabilidade de mutação
		// EX: 0.3 -> [1 1 1 0 0 0 0 0 0 0]
		List<Integer> chances = new ArrayList<Integer>();
		Integer goodShots = (int) (mutationProbability*10);
		Integer badShots = 10 - goodShots;
		for(int i = 0; i < goodShots; i++) {
			chances.add(1);
		}
		for(int i = 0; i < badShots; i++) {
			chances.add(0);
		}
		// Selecionar um index de 0 a 9 aleatoriamente
		Random rand = new Random();
		Integer sorted_index = rand.nextInt(10);
		// Verificar na lista se esse index contem um erro ou um acerto
		// Se acertou, entao permite a mutacao
		if(chances.get(sorted_index) == 1) {
			return true;
		} else { // Se errou, entao nao permite a mutacao
			return false;
		}
	}

//	private void mutation (int type) {
//		chromosome.sufferMutation(type);
//	}
	
}
