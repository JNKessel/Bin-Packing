package Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GeneticAlgorithm.GeneticAlgorithm;
import LocalSearch.LocalSearch;

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
    	Double AG_time;
    	Double BL_time;
    	
        System.out.println("Bin Packing Problem");
        
		// Rodar testes
		for (String test: test_files) {
			// Ler arquivo de teste para coletar informacoes
			FileData test_data = readFile(System.getProperty("user.dir") + "/src/SourceFiles/" + test);

			// Comeco da contagem de tempo do algoritmo
			start = System.currentTimeMillis();
			
			// Algoritmo Genetico
			test_totalBins = algoritmo_genetico.naturalSelection(test_data.getItems(),
					test_data.getBinCapacity(), test_data.getNumberOfItems());
			List<List<Integer>> Resultado_AG = new ArrayList<List<Integer>>(); 
			Resultado_AG = algoritmo_genetico.Get_the_Best();
			
			//Final da contagem de tempo do algoritmo
			elapsedTime = System.currentTimeMillis() - start;
			
			AG_time = elapsedTime/1000.0;
			
			// Comeco da contagem de tempo do algoritmo
			start = System.currentTimeMillis();
			
			// Busca Local
			test_totalBins = busca_local.localSearchStrategy(test_data.getItems(),
					test_data.getBinCapacity(), test_data.getNumberOfItems());
			
			//Final da contagem de tempo do algoritmo
			elapsedTime = System.currentTimeMillis() - start;
			
			BL_time = elapsedTime/1000.0;
			
			// Escrever arquivo de saida
			String output_file = "saida_" + test;
			writeFile(output_file, AG_time, Resultado_AG, algoritmo_genetico.totalGenerations(),
					BL_time, busca_local.getMainSolution(), busca_local.getCost());

			Resultado_AG.clear();
		}
			
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

    // Write output files for tests
    public static void writeFile (String file_name, Double tempo_AG, List<List<Integer>> saida_AG,
    		Integer custo_AG, Double tempo_BL, List<List<Integer>> saida_BL, Integer custo_BL) {
    	try {
    		String file_path = System.getProperty("user.dir") + "/src/OutFiles/" + file_name;
    		File arquivo = new File(file_path);
    		FileWriter file = new FileWriter(arquivo, false);
            BufferedWriter buffer = new BufferedWriter(file);

            buffer.write("--- Algoritmo Genetico");
            buffer.newLine();
            buffer.write("Tempo de Execução: " + String.valueOf(tempo_AG));
            buffer.newLine();
            buffer.write("Custo da Função Objetivo: " + String.valueOf(custo_AG));
            buffer.newLine();
            buffer.write("Número de bins final: " + String.valueOf(saida_AG.size())); //Number of bins
            buffer.newLine();
            buffer.write("Conteúdo dos bins");
            buffer.newLine();
            
            //Itens inside each bin
            for(List<Integer> BIN : saida_AG) {
            	for(int i=0; i < BIN.size(); i++) {
            		buffer.write(String.valueOf((int) BIN.get(i)));
            		buffer.write(" ");
            	}
            	buffer.newLine();
	        }

            buffer.newLine();
            buffer.write("--- Busca Local");
            buffer.newLine();
            buffer.write("Tempo de Execução: " + String.valueOf(tempo_BL));
            buffer.newLine();
            buffer.write("Custo da Função Objetivo: " + String.valueOf(custo_BL));
            buffer.newLine();
            buffer.write("Número de bins final: " + String.valueOf(saida_BL.size())); //Number of bins
            buffer.newLine();
            buffer.write("Conteúdo dos bins");
            buffer.newLine();
            
            //Itens inside each bin
            for(List<Integer> BIN : saida_BL) {
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
