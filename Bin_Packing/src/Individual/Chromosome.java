package Individual;
import java.util.ArrayList;
import java.util.List;

public class Chromosome {
	
	private List<Integer> chromosome;
	private CrossOvers crossOverMethods = new CrossOvers();
	
	public Chromosome(List<Integer> genes) {
		chromosome = new ArrayList<Integer>(genes);
	}
	
	public List<Chromosome> recombineChromossomes(Chromosome chromMae) {
		crossOverMethods.ParcialmenteMapeado(chromosome, chromMae.chromosome);
		Chromosome newChromossome1 = new Chromosome(crossOverMethods.getF1());
		Chromosome newChromossome2 = new Chromosome(crossOverMethods.getF2());
		List<Chromosome> newChromossomes12 = new ArrayList<Chromosome>();
		newChromossomes12.add(newChromossome1);
		newChromossomes12.add(newChromossome2);
		return newChromossomes12;
	}
	
	public void sufferMutation(int type) {
		
		if(type == 1){
			chromosome = (Mutation.Realocate(chromosome));
		}else if(type == 2){
			chromosome = (Mutation.Swap(chromosome));
		}else if(type == 3){
			chromosome = (Mutation.Two_Opt(chromosome));
		}
	}
	
	public List<Integer> getChromossomeList () {
		return chromosome;
	}
}
