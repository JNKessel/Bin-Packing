package LocalSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GeneticAlgorithm.GeneticAlgorithm.FitnessComparator;
import Individual.Individuo;
import Packing.Packing;
import Packing.Packing.ReverseWeightComparator;
import Packing.Packing.WeightComparator;

public class LocalSearch {
	private Integer totalBins;
	private List<Integer> items;
	private Integer binMaxCapacity;
	private Integer numberOfItems;
	
	private Integer READY = 1;
	private Integer NOT_READY = 0;
	private Integer terminal_condition = NOT_READY;
	
	List<List<Integer>> main_solution;
	List<List<List<Integer>>> neighbors = new ArrayList<List<List<Integer>>>();

	public Integer localSearchStrategy(List<Integer> initial_items, Integer binMaxCapacity, Integer numberOfItems) {
		// Copy Initial Problem Variables
		totalBins = numberOfItems;
		items = new ArrayList<Integer>(initial_items);
		this.binMaxCapacity = binMaxCapacity;
		this.numberOfItems = numberOfItems;

		// Gera solucao inicial
		generateInitialSolution();
		
		// Até a solução atual não poder sofrer melhora
		while(terminal_condition != READY) {
			// Aplica vizinhanças à solução atual
			applyNeighborhood();
			// Classifica as soluções geradas e a solução atual
			classifySolutions();
		}
		
		return totalBins;
	}
	
	private void generateInitialSolution() {
		// Ordenar os itens inicias em ordem decrescente de peso
		Collections.sort(items, Collections.reverseOrder());
		// Gerar uma solução inicial com FirstFit
		main_solution = Packing.FirstFit_LS(items, this.binMaxCapacity);
	}
	
	private void applyNeighborhood() {
		List<List<Integer>> main_solution_copy = new ArrayList<List<Integer>>(main_solution);
		
		// Aplicar vizinhança Troca 1-1
		List<List<Integer>> neighbor_1= Vizinhancas.exchange1(main_solution_copy);
		neighbor_1 = Packing.Realocate_LS(neighbor_1, binMaxCapacity);
		// Aplicar vizinhança Troca 2-0A
		List<List<Integer>> neighbor_2= Vizinhancas.exchange2_A(main_solution_copy, binMaxCapacity);
		neighbor_2 = Packing.Realocate_LS(neighbor_2, binMaxCapacity);
		// Aplicar vizinhança Troca 2-0B
		List<List<Integer>> neighbor_3= Vizinhancas.exchange2_B(main_solution_copy, binMaxCapacity);
		neighbor_3 = Packing.Realocate_LS(neighbor_3, binMaxCapacity);
		// Aplicar vizinhança Troca 2-1
		List<List<Integer>> neighbor_4= Vizinhancas.exchange2_C(main_solution_copy, binMaxCapacity);
		neighbor_4 = Packing.Realocate_LS(neighbor_4, binMaxCapacity);
		// Aplicar vizinhança Troca Perturbação
		List<List<Integer>> neighbor_5= Vizinhancas.shake(main_solution_copy, binMaxCapacity);
		neighbor_5 = Packing.Realocate_LS(neighbor_5, binMaxCapacity);
		// Aplicar vizinhança Troca Reconstrução
		List<List<Integer>> neighbor_6= Vizinhancas.restructure(main_solution_copy, binMaxCapacity);
		neighbor_6 = Packing.Realocate_LS(neighbor_6, binMaxCapacity);
		
		// Limpar lista de vizinhos
		neighbors.clear();
				
		// Adicionar novos vizinhos
		neighbors.add(neighbor_1);
		neighbors.add(neighbor_2);
		neighbors.add(neighbor_3);
		neighbors.add(neighbor_4);
		neighbors.add(neighbor_5);
		neighbors.add(neighbor_6);
	}
	
	private void classifySolutions() {
		// Agrupa as soluções atuais
		List<List<List<Integer>>> solutions = new ArrayList<List<List<Integer>>>();
		solutions.add(main_solution);
		solutions.addAll(neighbors);
		
		// Ordena as soluções atuais do menor numero de bins para o maior
		Collections.sort(solutions, new BinsComparator());
		
		// Pega a melhor solução (primeira)
		List<List<Integer>> bestSolution = solutions.get(0);
		
		// Se a melhor solução encontrada é a solução atual, e não algum vizinho
		if(bestSolution.equals(main_solution)) {
			// Então, acaba o problema
			terminal_condition = READY;
		} else {
			// Senão, a solução atual é atualizada para o melhor vizinho e a busca continua
			main_solution = bestSolution;
			totalBins = main_solution.size();
		}
	}
	
	// Compara o numero de bins dos individuos
	public class BinsComparator implements Comparator<List<List<Integer>>> {
	    @Override
	    public int compare(List<List<Integer>> o1, List<List<Integer>> o2) {
	    	Integer numberOfBins_o1 = o1.size();
	    	Integer numberOfBins_o2 = o2.size();
	        return numberOfBins_o1.compareTo(numberOfBins_o2);
	    }
	}
	
}