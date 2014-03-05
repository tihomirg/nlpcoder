package selection;

import java.util.Comparator;
import java.util.PriorityQueue;

import definitions.Declaration;

public class TopList {

	private int size;
	private PriorityQueue<Pair> top;
	
	public TopList(int size) {
		Comparator<Pair> comparator = new PairComparator();
		this.top = new PriorityQueue<Pair>(size+1, comparator);
		this.size = size;
	}

	public void put(Declaration decl, double probability) {
		// TODO Auto-generated method stub
		top.add(new Pair(decl, probability));
		if(top.size() > this.size){
			top.remove(); //first is the smallest one.
		}
	}

	@Override
	public String toString() {
		return "TopList [\n"+top+"\n]\n";
	}
	
}

class Pair {
	private Declaration decl;
	private double probability;

	public Pair(Declaration decl, double probability) {
		this.decl = decl;
		this.probability = probability;
	}

	public Declaration getDecl() {
		return decl;
	}

	public double getProbability() {
		return probability;
	}	
	
	@Override
	public String toString() {
		return "Pair [p=" + probability + "decl=" + decl + "]\n";
	}	
}

class PairComparator implements Comparator<Pair> {

	@Override
	public int compare(Pair p1, Pair p2) {
		// TODO Auto-generated method stub
		double probability1 = p1.getProbability();
		double probability2 = p2.getProbability();
		if (probability1 < probability2) return -1;
		else if (probability1 > probability2) return 1;
		else return 0;
	}

}