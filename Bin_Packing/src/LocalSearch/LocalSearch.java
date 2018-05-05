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
		
		// At� a solu��o atual n�o poder sofrer melhora
		while(terminal_condition != READY) {
			// Aplica vizinhan�as � solu��o atual
			applyNeighborhood();
			// Classifica as solu��es geradas e a solu��o atual
			classifySolutions();
		}
		
		return totalBins;
	}
	
	private void generateInitialSolution() {
		// Ordenar os itens inicias em ordem decrescente de peso
		Collections.sort(items, Collections.reverseOrder());
		// Gerar uma solu��o inicial com FirstFit
		main_solution = Packing.FirstFit_LS(items, this.binMaxCapacity);
	}
	
	private void applyNeighborhood() {
		List<List<Integer>> main_solution_copy = new ArrayList<List<Integer>>(main_solution);
		
		// Aplicar vizinhan�a Troca 1-1
		List<List<Integer>> neighbor_1= exchange1(main_solution_copy);
		// Aplicar vizinhan�a Troca 2-0A
		List<List<Integer>> neighbor_2= exchange2_A(main_solution_copy);
		// Aplicar vizinhan�a Troca 2-0B
		List<List<Integer>> neighbor_3= exchange2_B(main_solution_copy);
		// Aplicar vizinhan�a Troca 2-1
		List<List<Integer>> neighbor_4= exchange2_C(main_solution_copy);
		// Aplicar vizinhan�a Troca Perturba��o
		List<List<Integer>> neighbor_5= shake(main_solution_copy);
		// Aplicar vizinhan�a Troca Reconstru��o
		List<List<Integer>> neighbor_6= restructure(main_solution_copy);
		
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
		// Agrupa as solu��es atuais
		List<List<List<Integer>>> solutions = new ArrayList<List<List<Integer>>>();
		solutions.add(main_solution);
		solutions.addAll(neighbors);
		
		// Ordena as solu��es atuais do menor numero de bins para o maior
		Collections.sort(solutions, new BinsComparator());
		
		// Pega a melhor solu��o (primeira)
		List<List<Integer>> bestSolution = solutions.get(0);
		
		// Se a melhor solu��o encontrada � a solu��o atual, e n�o algum vizinho
		if(bestSolution.equals(main_solution)) {
			// Ent�o, acaba o problema
			terminal_condition = READY;
		} else {
			// Sen�o, a solu��o atual � atualizada para o melhor vizinho e a busca continua
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

	
		
//  public static void main(String[] args) {
//	  List<Integer> list = new ArrayList<Integer>();
//	  list.add(1);
//	  list.add(2);
//	  list.add(3);
//	  list.add(4);
//	  list.add(5);
//	  System.out.println(list);
//	  list.remove(Integer.valueOf(4));
//	  System.out.println(list);
//  }
}


//Troca 1-1: exchange1
//Troca 2-0A: exchange2_A
//Troca 2-0B: exchange2_B
//Troca 2-1: exchange2_C
//Perturba��o: shake
//Reconstru��o: restructure
