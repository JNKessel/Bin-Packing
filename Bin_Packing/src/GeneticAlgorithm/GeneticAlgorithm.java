package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Individual.Individuo;
import Packing.Packing;

public class GeneticAlgorithm {
	private Integer READY = 1;
	private Integer NOT_READY = 0;
	private Integer FIRST_FIT = 1;
	private Integer NEXT_FIT = 2;
	private Integer REALOCATE = 1;
	private Integer SWAP = 2;
	private Integer TWO_OPT = 3;
	private Integer SCRAMBLE = 4;

	private static HashMap<String, Integer> mapItems = new HashMap<String, Integer>();
	private Integer terminal_condition = NOT_READY;
	private Integer totalBins;
	private List<Integer> items;
	private List<Integer> Best_Solution = new ArrayList<Integer>(); //FIXME
	private Integer binMaxCapacity;
	private Integer numberOfItems;
	private Integer populationSize = 10; // Par
	private Double mutationChance = 0.1;

	private Integer generationNumber = 0;
	private Double generationsAverage;
	private Integer generationsBest;
	private Integer generationsWorst;
	private Integer generationsWithoutImprovement = 0;

	public GeneticAlgorithm() {
	}

	public Integer naturalSelection(List<Integer> initial_items, Integer binMaxCapacity, Integer numberOfItems) {
		// Copy Initial Problem Variables
		this.totalBins = numberOfItems;
		this.items = new ArrayList<Integer>(initial_items);
		this.binMaxCapacity = binMaxCapacity;
		this.numberOfItems = numberOfItems;
		
		Hash_Items(initial_items);

		// Garantir que o tamanho da população não é maior que o número total de
		// soluções (Fatorial(numberOfItems))
		if (numberOfItems < 4) { // 4 é o menor número para o qual Fatorial(numero) > 10 (tamanho da populacao)
			populationSize = Fatorial(numberOfItems);
		}

		List<Individuo> families;
		List<Individuo> population;

		// System.out.println("Primeira Geração:");

		// Gera população inicial de individuos
		population = firstGeneration(this.items);

		// for(Individuo i: population) {
		// System.out.println(i.getChromosome());
		// }

		// System.out.println("Classificação:");

		// Classificar geracao
		classifyGeneration(population, true);

		// System.out.println("Average: " + this.generationsAverage);
		// System.out.println("Best: " + this.generationsBest);
		// System.out.println("Worse: " + this.generationsWorst);

		while (terminal_condition != READY) {
			// System.out.println("Familias:");

			// Selecionar pais e gerar filhos
			families = generateFamilies(population);

			// for(Individuo i: families) {
			// System.out.println(i.getChromosome());
			// }

			// System.out.println("Classificação:");

			// Classificar geracao
			classifyGeneration(families, false);

			// System.out.println("Average: " + this.generationsAverage);
			// System.out.println("Best: " + this.generationsBest);
			// System.out.println("Worse: " + this.generationsWorst);

			// System.out.println("Próxima Geração:");

			// Selecionar individuos para a próxima geração
			population = nextGeneration(families);

			// for(Individuo i: population) {
			// System.out.println(i.getChromosome());
			// }

			// System.out.println("CONDICAO TERMINAL:" + terminal_condition);
		}

		for (Individuo i : population) {
			System.out.println("Chromossome: " + i.getChromosome() + "   " + "Fitness: " + i.getFitness());
		}

		System.out.println("Average: " + this.generationsAverage);
		System.out.println("Best: " + this.generationsBest);
		System.out.println("Worse: " + this.generationsWorst);
		System.out.println("Total Generations: " + this.generationNumber);
		System.out.println("Without Improvement: " + this.generationsWithoutImprovement);

		totalBins = population.get(0).getFitness();
		
		if(Best_Solution.isEmpty() == false)
			Best_Solution.clear();
		
		this.Best_Solution.addAll(population.get(0).getChromosome()); //FIXME

		return totalBins;
	}

	private List<Individuo> firstGeneration(List<Integer> initial_items) {
		List<Integer> initial_items_copy = new ArrayList<Integer>(initial_items);
		List<Individuo> initial_population = new ArrayList<Individuo>();
		Individuo novo;

		// Gerar um individuo com ordenacao natural
		initial_population.add(new Individuo(initial_items_copy));

		// Gerar um individuo com ordenacao decrescente
		if (initial_population.size() != populationSize) {
			Collections.sort(initial_items_copy, Collections.reverseOrder());
			try {
				for (Individuo i : initial_population) {
					if (i.getChromosome() != initial_items_copy) {
						novo = new Individuo(initial_items_copy);
						initial_population.add(novo);
					}
				}
			} catch (Exception e) {
				// Do nothing
			}
		}

		// Gerar um individuo com ordenacao crescente
		if (initial_population.size() != populationSize) {
			Collections.sort(initial_items_copy);
			try {
				for (Individuo i : initial_population) {
					if (i.getChromosome() != initial_items_copy) {
						novo = new Individuo(initial_items_copy);
						initial_population.add(novo);
					}
				}
			} catch (Exception e) {
				// Do nothing
			}

		}

		// Gerar o resto dos individuos com ordenacao aleatoria
		while (initial_population.size() != populationSize) {
			Collections.shuffle(initial_items_copy);
			try {
				for (Individuo i : initial_population) {
					if (i.getChromosome() != initial_items_copy) {
						novo = new Individuo(initial_items_copy);
						initial_population.add(novo);
					}
				}
			} catch (Exception e) {
				// Do nothing
			}

		}

		generationNumber += 1;

		return initial_population;
	}

	
	private List<Individuo> generateFamilies(List<Individuo> old_population) {
		// System.out.println("Selecionar Pais");

		// Selecionar pais
		List<List<Individuo>> population_parents = selectParents(old_population);

		// System.out.println("Gerar Filhos");

		// Recombinar pais para gerar filhos
		List<Individuo> population_sons = generateChildren(population_parents);

		// System.out.println("Juntar Pais e Filhos");

		// Formar familias (juntar uma lista de individuos com pais e filhos)
		List<Individuo> families = joinParentsChildren(old_population, population_sons);
		return families;
	}

	
	private List<List<Individuo>> selectParents(List<Individuo> old_population) {
		List<Individuo> old_population_copy = new ArrayList<Individuo>(old_population);
		List<List<Individuo>> parents = new ArrayList<List<Individuo>>();

		// Ate todos os pais serem selecionados
		while (parents.size() != populationSize / 2 && old_population_copy.size() > 0) {
			// Selecionar individuos aleatorios para serem pais
			Individuo parent1 = chooseRandomParent(old_population_copy);
			Individuo parent2 = chooseRandomParent(old_population_copy);
			// Adicionar eles como pais
			List<Individuo> parents12 = new ArrayList<Individuo>();
			parents12.add(parent1);
			parents12.add(parent2);
			parents.add(parents12);
		}

		return parents;
	}

	private Individuo chooseRandomParent(List<Individuo> old_population_copy) {
		Random random = new Random();
		// Escolhe aleatoriamente um individuo na lista para ser pai
		Individuo parent = old_population_copy.get(random.nextInt(old_population_copy.size()));
		// Retira ele da lista (evitar repeticoes de pais)
		old_population_copy.remove(parent);
		return parent;
	}

	private List<Individuo> generateChildren(List<List<Individuo>> parents) {
		List<Individuo> children = new ArrayList<Individuo>();

		// Para todos os pares de pais
		for (List<Individuo> pair_parents : parents) {
			if (pair_parents.size() != 2) {
				System.out.println("Error in generateChildren: pair_parents doesnt have 2 parents");
				break;
			}
			// Recombinar pais
			Individuo parent1 = pair_parents.get(0);
			Individuo parent2 = pair_parents.get(1);
			// Usar para mutacao: RELOCATE, SWAP ou TWO_OPT
			List<Individuo> pair_children = parent1.generateChild(parent2, mutationChance,SCRAMBLE ); //FIXME TODO ALTERAR TIPO DE MUTACAO AQUI
			// Adicionar os filhos gerados a lista final de filhos
			for (Individuo child : pair_children) {
				children.add(child);
			}
		}

		return children;
	}

	private List<Individuo> joinParentsChildren(List<Individuo> parents, List<Individuo> children) {
		// Formar familias (juntar uma lista de individuos com pais e filhos)
		List<Individuo> families = new ArrayList<Individuo>();
		families.addAll(parents);
		families.addAll(children);
		return families;
	}

	private List<Individuo> nextGeneration(List<Individuo> old_population) {
		// Definir quais individuos das familias passam para a proxima
		// geracao baseada na sua classificacao
		List<Individuo> old_population_copy = new ArrayList<Individuo>(old_population);
		List<Individuo> new_population = new ArrayList<Individuo>();

		// Ordenar lista do menor fitness para o maior
		Collections.sort(old_population, new FitnessComparator());

		for (Individuo i : old_population) {
			if (new_population.size() < populationSize) {
				try {
					if (new_population.size() == 0) {
						new_population.add(i);
						old_population_copy.remove(i);
					} else {
						for (Individuo j : new_population) {
							if (j.getChromosome() != i.getChromosome()) {
								new_population.add(i);
								old_population_copy.remove(i);
							}
						}
					}
				} catch (Exception e) {
					// Do nothing
				}
			} else {
				break;
			}
		}

		while (new_population.size() < populationSize) {
			new_population.add(old_population_copy.get(0));
			old_population_copy.remove(0);
		}

		generationNumber += 1;
		return new_population;
	}

	// Compara fitness dos individuos
	public class FitnessComparator implements Comparator<Individuo> {
		@Override
		public int compare(Individuo o1, Individuo o2) {
			return o1.getFitness().compareTo(o2.getFitness());
		}
	}

	private void classifyGeneration(List<Individuo> population, boolean first_gen) {
		// Medidas de classificacao: média, melhor e pior fitness
		Integer bestFitness = this.numberOfItems;
		Integer worstFitness = 0;
		Double average = 0.0;

		// Pontos para mudar probabilidade de mutação
		// mutationChance aumenta quando a populacao esta se estabilizando
		// ou as medidas de avaliacao da populacao nao estao melhorando
		mutationChance = 0.0;

		// Para todos os individuos da populacao
		for (Individuo i : population) {
			// Calcular o fitness
			calcFitness(i, FIRST_FIT);       			//FIXME TODO AQUI MUDA NEXT/FIRST FIT
			// Alterar medidas de classificacao
			if (i.getFitness() < bestFitness) {
				bestFitness = i.getFitness();
			}
			if (i.getFitness() > worstFitness) {
				worstFitness = i.getFitness();
			}
			average += i.getFitness();
		}
		average = average / population.size();

		// Atribuir variaveis na primeira geração
		if (first_gen) {
			generationsAverage = average;
			generationsBest = bestFitness;
			generationsWorst = worstFitness;
		}

		// Avaliar média das geracoes para o total de bins utilizados
		if (average >= generationsAverage) {
			mutationChance += 0.1;
		}
		generationsAverage = (generationsAverage + average) / 2;
		// Avaliar melhor número de bins utilizado em todas as geracoes
		// System.out.println("Generations Best" + this.generationsBest);
		// System.out.println("Actual Best" + bestFitness);
		if (bestFitness >= generationsBest) {
			mutationChance += 0.1;
			generationsWithoutImprovement += 1;
		} else {
			generationsWithoutImprovement = 0;
			generationsBest = bestFitness;
		}
		// System.out.println("without improvementt" +
		// this.generationsWithoutImprovement);
		// Avaliar pior número de bins utilizado em todas as geracoes
		if (worstFitness >= generationsWorst) {
			mutationChance += 0.1;
		} else {
			generationsWorst = worstFitness;
		}
		// Avaliar o tempo da geracao, se esta nova ou velha
		if (generationsWithoutImprovement > 15) {
			mutationChance += 0.3;
		} else if (generationsWithoutImprovement > 20) {
			mutationChance += 0.5;
		} else if (generationsWithoutImprovement > 25) {
			mutationChance += 0.7;
		}

		if (generationNumber > 2500 || generationsWithoutImprovement > 2000) {   //FIXME TODO AQUI MUDA CONDICAO DE PARADA
			terminal_condition = READY;
		}

	}

	// Fatorial
	public Integer Fatorial(int num) {
		if (num <= 1) {
			return 1;
		} else {
			return Fatorial((num - 1) * num);
		}
	}

	private void calcFitness(Individuo i, Integer type) {
		// Calcula o Fitness do individuo
		Integer fitness = null;
		if (type == 1) {
			fitness = Packing.FirstFit_GN(i.getChromosome(), i.getChromosome().size(), binMaxCapacity);
		} else if (type == 2) {
			fitness = Packing.NextFit_GN(i.getChromosome(), i.getChromosome().size(), binMaxCapacity);
		}
		if (fitness != null) {
			// Muda o valor do fitness no individuo
			i.setFitness(fitness);
		} else {
			System.out.println("Erro na calcFitness: fitness is null, type arg must be wrong");
		}
	}
	
	public void Hash_Items(List<Integer> parent) {

		//HashMap<String, Integer> mapItems = new HashMap<String, Integer>();
		
		// Associa cada valor dos elementos do pai a uma string atraves de um hashmap. A string eh 
		// representada por "N" seguido de um sufixo, sendo este, um valor inteiro de 1 ate o tamanho do pai
		for(int i=0; i < parent.size(); i++) {
			mapItems.put("N"+(i+1), parent.get(i));
		}
	}
	
	public static HashMap<String, Integer> GetHashMap() {
		return mapItems;
	}
	
	// FIXME
	public List<Integer> Get_the_Best() {
		return this.Best_Solution;
	}

}
	

	// public static void main(String[] args) {
	// System.out.println(((Integer) 1).compareTo((Integer) 2));
	// System.out.println("Hello There!");
	// List<Integer> list = new ArrayList<Integer>();
	// list.add(7);
	// list.add(1);
	// list.add(10);
	// list.add(2);
	// list.add(6);
	// System.out.println(list);
	// System.out.println(list.get(0));
	// while(list.size() != 0) {
	// Random random = new Random();
	// Integer parent = list.get(random.nextInt(list.size()));
	// list.remove(parent);
	// System.out.println(parent);
	// System.out.println(list);
	// }
	// }

