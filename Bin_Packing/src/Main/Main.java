package Main;
import GeneticAlgorithm.*;
import Individual.Mutation;
import LocalSearch.LocalSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
			List<List<Integer>> Resultado_AG = new ArrayList<List<Integer>>(); 
			Resultado_AG = algoritmo_genetico.Get_the_Best();
			System.out.print("Main Solution AG: "+ Resultado_AG + "\n");
			
			
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
			System.out.println("Total time: " + elapsedTime/1000.0 + "s\n");
			
			writeFile(System.getProperty("user.dir") + "/src/Resultado_" + test, Resultado_AG, test_data.getBinCapacity());
			Resultado_AG.clear();
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
		System.out.println("Total time: " + elapsedTime/1000.0 + "s\n");

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
    
    public static void writeFile (String file_path, List<List<Integer>> Saida, int binMaxCapacity) {
    	try {
            //FIXME Nao estou conseguindo imprimir os arquivos de saida...
    		File arquivo = new File(file_path);
    		arquivo.createNewFile();
    		FileWriter file = new FileWriter(arquivo);
            BufferedWriter buffer = new BufferedWriter(new FileWriter(file_path));

            buffer.write(String.valueOf(Saida.size())); //Number of bins
            buffer.newLine();
            buffer.write(String.valueOf(binMaxCapacity)); //Capacity of bins
            buffer.newLine();
            
            //Itens inside each bin
            for(List BIN : Saida) {
            	for(int i=0; i < BIN.size(); i++) {
            		buffer.write(String.valueOf((int) BIN.get(i)));
            		buffer.write(" ");
            	}
            	buffer.newLine();
	        }
            buffer.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}


//
////Rodar testes
//		for (String test: test_files) {
//			// Ler arquivo de teste para coletar informacoes
//			FileData test_data = readFile(System.getProperty("user.dir") + "/src/SourceFiles/" + test);
//			
//			System.out.println("Number of items: " + test_data.getNumberOfItems());
//			System.out.println("Bin Maximum Capacity: " + test_data.getBinCapacity());
//			System.out.println("Items: " + test_data.getItems());
//			
//			//TODO REMOVER DAQUI
//			Integer numberOfItems = test_data.getNumberOfItems();
//			binCap = test_data.getBinCapacity();
//			List<Integer> items = new ArrayList<Integer>();
//			items = test_data.getItems();
//			//TODO ATE AQUI
//
//			System.out.println("--- Genetic Algorithm");
//
//			// Comeco da contagem de tempo do algoritmo
//			long start1 = System.currentTimeMillis();
//			
//			// Algoritmo Genetico
//			test_totalBins = algoritmo_genetico.naturalSelection(test_data.getItems(),
//					test_data.getBinCapacity(), test_data.getNumberOfItems());
//			
//			//TODO REMOVER DAQUI
//			List<Integer> daBEST = new ArrayList<Integer>();
//			daBEST = algoritmo_genetico.Get_the_Best();
//			System.out.println("MELHOR:");
//			System.out.println(daBEST);
//			Integer[][] melhor = new Integer[test_totalBins][];
//																			//FIXME TODO mudar pra NEXTFIT / FIRSTFIT AQUI
//			melhor = Packing.FirstFit_GN_Saida(daBEST,numberOfItems,binCap);
//			
//	        List<List<Integer>> Padrao_de_saida = new ArrayList<List<Integer>>();
//	        
//	        
//	        
//	        for(int i=0; i < test_totalBins; i++) {
//	        	//Inicializa toda a Lista de ArrayLists
//	        	Padrao_de_saida.add(new ArrayList<Integer>());
//	        }
//	        for(int i=0; i < melhor.length; i++) {
//	        	int binNUMBER = melhor[i][0];
//	        	int elemento = melhor[i][1];
//	        	Padrao_de_saida.get(binNUMBER-1).add(elemento);
//	        }	
//	        
//	        writeFile(System.getProperty("user.dir") + "/src/OutputFiles/Fk_t120_00.txt", Padrao_de_saida, binCap);
//	        
//	        for(int i=0; i < melhor.length; i++) {
//	        	melhor[i] = null;	        	
//	        }
//	        Padrao_de_saida.clear();
//			//TODO ATE AQUI
//			
//			//Final da contagem de tempo do algoritmo
//			long elapsedTime1 = System.currentTimeMillis() - start1;
//			
//			System.out.println("Number of bins = " + test_totalBins);
//			System.out.println("Total time: " + elapsedTime1/1000.0 + "s\n\n");
//		}
//			
// }
