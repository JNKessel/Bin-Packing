package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Individual.Individuo;

public class GeneticAlgorithm {
	private Integer READY = 1;
	private Integer NOT_READY = 1;
	private Integer terminal_condition = NOT_READY;
	private Integer totalBins;
	private List<Integer> items;
	private Integer binMaxCapacity;
	private Integer numberOfItems;
	private Integer populationSize = 10; // Par
	
	public GeneticAlgorithm() {
	}
	
	public Integer naturalSelection(List<Integer> initial_items, Integer binMaxCapacity, Integer numberOfItems) {
		// Copy Initial Problem Variables
		this.totalBins = numberOfItems;
		this.items = new ArrayList<Integer>(initial_items);
		this.binMaxCapacity = binMaxCapacity;
		this.numberOfItems = numberOfItems;
		
		// Garantir que o tamanho da população não é maior que o número total de soluções (Fatorial(numberOfItems))
		if(numberOfItems < 4) { // 4 é o menor número para o qual Fatorial(numero) > 10 (tamanho da populacao)
			populationSize = Fatorial(numberOfItems);
		}

		List<Individuo> families;
		List<Individuo> population;
		
		// Gera população inicial de individuos
		population = firstGeneration(this.items);
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
		// Recombinar pais para gerar filhos
		// TODO: CrossOver dos pais adicionando os filhos gerados a lista final
		
		
		List<Individuo> children = new ArrayList<Individuo>();
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
		// TODO: Definir quais individuos das familias passam para a proxima
		// geracao baseada na sua classificacao
		List<Individuo> new_population = new ArrayList<Individuo>();
		return new_population;
	}
	
	private List<Individuo> classifyGeneration(List<Individuo> old_population) {
		// TODO: Classificar os individuos da populacao
		List<Individuo> new_population = new ArrayList<Individuo>();
		return new_population;
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
	
    public static void main(String[] args) {
    	System.out.println("Hello There!");
    	List<Integer> list = new ArrayList<Integer>();
    	list.add(7);
    	list.add(1);
    	list.add(10);
    	list.add(2);
    	list.add(6);
    	
    	while(list.size() != 0) {
			Random random = new Random();
			Integer parent = list.get(random.nextInt(list.size()));
			list.remove(parent);
			System.out.println(parent);
			System.out.println(list);
    	}

    }

}
