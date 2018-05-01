package Individual;

import java.util.List;

import Main.FileData;
import Packing.Packing;

public class Individuo {
	
	private Chromosome chromosome;
	private int fitness;
	
	public Individuo(List<Integer> genes){
		
		chromosome = new Chromosome();
		calcFitness();
		
	}
	
	public Individuo(Individuo pai, Individuo mae){
		
		chromosome = new Chromosome(pai.chromosome, mae.chromosome);
		calcFitness();
		
	}
	
	 public int getFitness(){
		 return fitness;
	 }
	 
	 private void calcFitness(){
		 fitness = Packing.FirstFit(chromosome.chromosome, chromosome.chromosome.length, FileData.binMaxCapacity);
	 }

}
