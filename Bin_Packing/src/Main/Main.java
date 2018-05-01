package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static String numberOfItems;
	private static String binMaxCapacity;
	public static List<Integer> items = new ArrayList<Integer>();;
	
    public static void main(String[] args) {
        System.out.println("Starting Bin Packing Problem");
		readFile(System.getProperty("user.dir") + "/src/SourceFiles/Falkenauer_t120_01.txt");
		System.out.println("Number of items: " + numberOfItems);
		System.out.println("Bin Maximum Capacity: " + binMaxCapacity);
		System.out.println("Items: " + items);
		
		//Comeco da contagem de tempo do algoritmo
		long start = System.currentTimeMillis();
		
		//Chamada do algoritmo
		
		//Final da contagem de tempo do algoritmo
		long elapsedTime = System.currentTimeMillis() - start;
		//System.out.println("Number of bins = " + numberOfBins);
		System.out.println("Total time: " + elapsedTime/1000.0 + "s");
    }
   
    // Read files by path and separate number of items, bin max capacity and items
    public static void readFile (String file_path) {
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(file_path));
    	    numberOfItems = br.readLine();
    	    binMaxCapacity = br.readLine();
    	    String item = br.readLine();
    	    while (item != null) {
    	    	items.add(Integer.parseInt(item));
    	        item = br.readLine();
    	    }
    	    br.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
