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
	
	public void sufferMutation() {
		// TODO: Implement mutation by swap, 2-opt or another method
	
	}
	
	public List<Integer> getChromossomeList () {
		return chromosome;
	}
}
