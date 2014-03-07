package selection;

import java.util.Comparator;
import java.util.PriorityQueue;

import selection.parser.one.Word;

import definitions.Declaration;

public class TopList {

	private int size;
	private PriorityQueue<Pair> top;
	private Word word;

	public TopList(int size) {
		this(null, size);
	}

	public TopList(Word word, int size) {
		Comparator<Pair> comparator = new PairComparator();
		this.top = new PriorityQueue<Pair>(size+1, comparator);
		this.size = size;
		this.word = word;
	}

	public void put(RichDeclaration richDeclaration, double probability) {
		// TODO Auto-generated method stub
		top.add(new Pair(richDeclaration, probability));
		if(top.size() > this.size){
			top.remove(); //first is the smallest one.
		}
	}
	
	public Word getWord() {
		return word;
	}	

	@Override
	public String toString() {
		return "TopList [\n"+top+"\n]\n";
	}
	
}

class Pair {
	private RichDeclaration rd;
	private double probability;

	public Pair(RichDeclaration rd, double probability) {
		this.rd = rd;
		this.probability = probability;
	}

	public RichDeclaration getRichDeclaration() {
		return rd;
	}

	public double getProbability() {
		return probability;
	}	
	
	@Override
	public String toString() {
		return "Pair [p=" + probability + ", rd=" + rd + "]\n";
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