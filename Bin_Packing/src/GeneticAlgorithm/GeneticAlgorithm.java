package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import Individual.Individuo;
import Packing.Packing;

public class GeneticAlgorithm {
	private Integer READY = 1;
	private Integer NOT_READY = 1;
	private Integer FIRST_FIT = 1;
	private Integer NEXT_FIT = 2;
	
	private Integer terminal_condition = NOT_READY;
	private Integer totalBins;
	private List<Integer> items;
	private Integer binMaxCapacity;
	private Integer numberOfItems;
	private Integer populationSize = 10; // Par
	
	private Integer generationNumber = 0;
	private Double generationsAverage;
	private Integer generationsBest;
	private Integer generationsWorst;

	
	public GeneticAlgorithm() {
	}
	
	public Integer naturalSelection(List<Integer> initial_items, Integer binMaxCapacity, Integer numberOfItems) {
		// Copy Initial Problem Variables
		this.totalBins = numberOfItems;
		this.items = new ArrayList<Integer>(initial_items);
		this.binMaxCapacity = binMaxCapacity;
		this.numberOfItems = numberOfItems;
				
		// Garantir que o tamanho da popula��o n�o � maior que o n�mero total de solu��es (Fatorial(numberOfItems))
		if(numberOfItems < 4) { // 4 � o menor n�mero para o qual Fatorial(numero) > 10 (tamanho da populacao)
			populationSize = Fatorial(numberOfItems);
		}

		List<Individuo> families;
		List<Individuo> population;
		
		// Gera popula��o inicial de individuos
		population = firstGeneration(this.items);
		// Classificar geracao
		classifyGeneration(population, true);
		while(terminal_condition == READY) {
			// Selecionar pais e gerar filhos
			families = generateFamilies(population);
			// Classificar geracao
			classifyGeneration(families, false);
			// Selecionar individuos para a pr�xima gera��o
			population = nextGeneration(population);
		}
		return totalBins;
	}
	
	private List<Individuo> firstGeneration(List<Integer> initial_items) {
		List<Integer> initial_items_copy = new ArrayList<Integer>(initial_items);
		List<Individuo> initial_population = new ArrayList<Individuo>();

		// Gerar um individuo com ordenacao natral
		initial_population.add(new Individuo(initial_items_copy));
		
		// Gerar um individuo com ordenacao decrescente
		Collections.sort(initial_items_copy, Collections.reverseOrder());
		Individuo novo = new Individuo(initial_items_copy);
		if(!initial_population.contains(novo)) {
			initial_population.add(novo);			
		}
		
		// Gerar um individuo com ordenacao crescente
		Collections.sort(initial_items_copy);
		novo = new Individuo(initial_items_copy);
		if(!initial_population.contains(novo)) {
			initial_population.add(novo);			
		}

		// Gerar o resto dos individuos com ordenacao aleatoria
		while(initial_population.size() != populationSize) {
			Collections.shuffle(initial_items_copy);
			novo = new Individuo(initial_items_copy);
			if(!initial_population.contains(novo)) {
				initial_population.add(novo);
			}
		}
		
		generationNumber += 1;
		
		return initial_population;
	}
	
	private List<Individuo> generateFamilies(List<Individuo> old_population) {
		// Selecionar pais
		List<List<Individuo>> population_parents = selectParents(old_population);
		// Recombinar pais para gerar filhos
		List<Individuo> population_sons = generateChildren(population_parents);
		// Formar familias (juntar uma lista de individuos com pais e filhos)
		List<Individuo> families = joinParentsChildren(old_population, population_sons);
		return families;
	}
	
	private List<List<Individuo>> selectParents(List<Individuo> old_population) {
		List<Individuo> old_population_copy = new ArrayList<Individuo>(old_population);
		List<List<Individuo>> parents = new ArrayList<List<Individuo>>();
		
		// Ate todos os pais serem selecionados
		while(parents.size() != populationSize/2) {
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
		for (List<Individuo> pair_parents: parents) {
			if(pair_parents.size() != 2) {
				System.out.println("Error in generateChildren: pair_parents doesnt have 2 parents");
				break;
			}
			// Recombinar pais
			Individuo parent1 = pair_parents.get(0);
			Individuo parent2 = pair_parents.get(1);
			List<Individuo> pair_children = parent1.generateChild(parent2);
			// Adicionar os filhos gerados a lista final de filhos
			for(Individuo child: pair_children) {
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
		List<Individuo> new_population = new ArrayList<Individuo>();
		
		// Ordenar lista do menor fitness para o maior
		Collections.sort(old_population, new FitnessComparator());
		
		for(Individuo i: old_population) {
			new_population.add(i);
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
		// Medidas de classificacao: m�dia, melhor e pior fitness
		Integer bestFitness = this.numberOfItems;
		Integer worstFitness = 0;
		Double average = 0.0;
		
		// Pontos para mudar probabilidade de muta��o
		Integer mutationPoints = 0;
		
		// Para todos os individuos da populacao
		for(Individuo i: population) {
			// Calcular o fitness
			calcFitness(i, FIRST_FIT);
			// Alterar medidas de classificacao
			if(i.getFitness() < bestFitness) {
				bestFitness = i.getFitness();
			}
			if(i.getFitness() > worstFitness) {
				worstFitness = i.getFitness();
			}
			average += i.getFitness();
		}
		average = average / population.size();
		
		// Atribuir variaveis na primeira gera��o
		if(first_gen) {
			generationsAverage = average;
			generationsBest = bestFitness;
			generationsWorst = worstFitness;
		}
		
		// Avaliar m�dia das geracoes para o total de bins utilizados
		if(average > generationsAverage) {
			mutationPoints += 1;
		}
		generationsAverage = (generationsAverage + average) / 2;
		// Avaliar melhor n�mero de bins utilizado em todas as geracoes
		if(bestFitness > generationsBest) {
			mutationPoints += 1;
		} else {
			generationsBest = bestFitness;
		}
		// Avaliar pior n�mero de bins utilizado em todas as geracoes
		if(worstFitness > generationsWorst) {
			mutationPoints += 1;
		} else {
			generationsWorst = worstFitness;
		}
		// Avaliar o tempo da geracao, se esta nova ou velha
		if(generationNumber > 500) {
			mutationPoints += 3;
		}
		
		// TODO: Alterar mutacao de acordo com mutationPoints
		// Aumentar probabilidade de mutacao se os mutationPoints forem altos
		// Diminuir probabilidade de mutacao se os mutationPoints forem baixos
		// MutationPoints aumentam quando a populacao esta ficando mais velha ou as medidas de avaliacao da populacao nao estao melhorando		
	}
	
	// Fatorial
	public Integer Fatorial(int num) {
		if(num <= 1) {
			return 1;
		}
		else {
			return Fatorial((num - 1) * num);
		}
	}
	
	 private void calcFitness(Individuo i, Integer type) {
		 // Calcula o Fitness do individuo
		 Integer fitness = null;
		 if(type == 1) {
			 fitness = Packing.FirstFit(i.getChromosome(), i.getChromosome().size(), binMaxCapacity);
		 }
		 else if(type == 2) {
			 fitness = Packing.NextFit(i.getChromosome(), i.getChromosome().size(), binMaxCapacity);
		 }
		 if(fitness != null) {
			 // Muda o valor do fitness no individuo
			 i.setFitness(fitness);
		 } else {
			 System.out.println("Erro na calcFitness: fitness is null, type arg must be wrong");
		 }
	 }

	
    public static void main(String[] args) {
    	System.out.println(((Integer) 1).compareTo((Integer) 2));
//    	System.out.println("Hello There!");
//    	List<Integer> list = new ArrayList<Integer>();
//    	list.add(7);
//    	list.add(1);
//    	list.add(10);
//    	list.add(2);
//    	list.add(6);
//    	System.out.println(list);
//    	System.out.println(list.get(0));
//    	while(list.size() != 0) {
//			Random random = new Random();
//			Integer parent = list.get(random.nextInt(list.size()));
//			list.remove(parent);
//			System.out.println(parent);
//			System.out.println(list);
//    	}
    }

}
