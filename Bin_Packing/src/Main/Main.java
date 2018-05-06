package Main;
import GeneticAlgorithm.*;
import LocalSearch.LocalSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static String[] test_files = {"Falkenauer_t120_01.txt",
										  "Falkenauer_t60_00.txt",
										  "Falkenauer_u120_02.txt",
										  "Falkenauer_u250_04.txt",
										  "Falkenauer_u500_05.txt"};
	
    public static void main(String[] args) {
    	GeneticAlgorithm algoritmo_genetico = new GeneticAlgorithm();
    	LocalSearch busca_local = new LocalSearch();
    	Integer test_totalBins;
    	long start;
    	long elapsedTime;
    	
        System.out.println("Bin Packing Problem");

        // Funcao para rodar nossos testes
//        rodarTeste();
        
		// Rodar testes
		for (String test: test_files) {
			// Ler arquivo de teste para coletar informacoes
			FileData test_data = readFile(System.getProperty("user.dir") + "/src/SourceFiles/" + test);
			
			System.out.println("----- File " + test);
			
			System.out.println("Number of items: " + test_data.getNumberOfItems());
			System.out.println("Bin Maximum Capacity: " + test_data.getBinCapacity());
			System.out.println("Items: " + test_data.getItems());

			System.out.println("--- Genetic Algorithm");

			// Comeco da contagem de tempo do algoritmo
			start = System.currentTimeMillis();
			
			// Algoritmo Genetico
			test_totalBins = algoritmo_genetico.naturalSelection(test_data.getItems(),
					test_data.getBinCapacity(), test_data.getNumberOfItems());
			
			//Final da contagem de tempo do algoritmo
			elapsedTime = System.currentTimeMillis() - start;
			
			System.out.println("Number of bins = " + test_totalBins);
			System.out.println("Total time: " + elapsedTime/1000.0 + "s");
			
			System.out.println("--- Local Search");
			
			// Comeco da contagem de tempo do algoritmo
			start = System.currentTimeMillis();
			
			// Algoritmo Genetico
			test_totalBins = busca_local.localSearchStrategy(test_data.getItems(),
					test_data.getBinCapacity(), test_data.getNumberOfItems());
			
			//Final da contagem de tempo do algoritmo
			elapsedTime = System.currentTimeMillis() - start;
			
			System.out.println("Number of bins = " + test_totalBins);
			System.out.println("Total time: " + elapsedTime/1000.0 + "s");

		}
			
    }
    
    public static void rodarTeste() {
    	GeneticAlgorithm algoritmo_genetico = new GeneticAlgorithm();
    	Integer test_totalBins;
    	
        Integer numberOfItems = 7;
        Integer binCap = 10;
        List<Integer> items = new ArrayList<Integer>();
        for(int i = 1; i < 8; i++) {
        	items.add(i);
        }
        
		System.out.println("Number of items: " + numberOfItems);
		System.out.println("Bin Maximum Capacity: " + binCap);
		System.out.println("Items: " + items);

		System.out.println("--- Genetic Algorithm");

		// Comeco da contagem de tempo do algoritmo
		long start = System.currentTimeMillis();
		
		// Algoritmo Genetico
		test_totalBins = algoritmo_genetico.naturalSelection(items, binCap, numberOfItems);
		
		//Final da contagem de tempo do algoritmo
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("Number of bins = " + test_totalBins);
		System.out.println("Total time: " + elapsedTime/1000.0 + "s");

    }
   
    // Read files by path and separate number of items, bin max capacity and items
    public static FileData readFile (String file_path) {
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(file_path));
    	    Integer numberOfItems = Integer.parseInt(br.readLine());
    	    Integer binMaxCapacity = Integer.parseInt(br.readLine());
    	    List<Integer> items = new ArrayList<Integer>();;
    	    String item = br.readLine();
    	    while (item != null) {
    	    	items.add(Integer.parseInt(item));
    	        item = br.readLine();
    	    }
    	    br.close();
    	    FileData data = new FileData(items, binMaxCapacity, numberOfItems);
        	return data;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		return null;
    }
}
