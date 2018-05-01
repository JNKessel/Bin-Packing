package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import Individual.Individuo;

public class GeneticAlgorithm {
	private Integer READY = 1;
	private Integer NOT_READY = 1;
	private Integer terminal_condition = NOT_READY;
	
	public GeneticAlgorithm(List<Integer> initial_items){
		List<Individuo> families;
		// Gera população inicial de individuos
		List<Individuo> population = firstGeneration(initial_items);
		// Classificar geracao
		classifyGeneration(population);
		while(terminal_condition == READY) {
			// Selecionar pais e gerar filhos
			families = generateFamilies(population);
			// Classificar geracao
			classifyGeneration(families);
			// Selecionar individuos para a próxima geração
			population = nextGeneration(population);
		}
	}
	
	private List<Individuo> firstGeneration(List<Integer> initial_items) {
		List<Individuo> initial_population = new ArrayList<Individuo>();
		return initial_population;
	}
	
	private List<Individuo> generateFamilies(List<Individuo> old_population) {
		List<Individuo> new_population = new ArrayList<Individuo>();
		return new_population;
	}

	private List<Individuo> nextGeneration(List<Individuo> old_population) {
		List<Individuo> new_population = new ArrayList<Individuo>();
		return new_population;
	}
	
	private List<Individuo> classifyGeneration(List<Individuo> old_population) {
		List<Individuo> new_population = new ArrayList<Individuo>();
		return new_population;
	}

}
