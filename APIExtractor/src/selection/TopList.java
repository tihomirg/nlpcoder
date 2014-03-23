package selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import selection.parser.one.Word;

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

	public void tryPut(RichDeclaration richDeclaration, double score) {
		Pair pair = new Pair(richDeclaration, score);

		if (top.contains(pair)){
			top.remove(pair);
			top.add(pair);
		} else {
			top.add(pair);
			if(top.size() > this.size){
				top.remove(); //first is the smallest one.
			}
		}
	}

	public Word getWord() {
		return word;
	}	

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("For word: "+ word.getLemma()+"\n");

		List<String> list = new LinkedList<String>();
		int length = top.size();
		while(!top.isEmpty()){
			Pair curr = top.remove();
			list.add(curr+"\n");
		}

		String[] array = list.toArray(new String[length]);

		for(int i = length-1; i >= 0; i--){
			sb.append(array[i]);
		}

		return sb.toString();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rd == null) ? 0 : rd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (rd == null) {
			if (other.rd != null)
				return false;
		} else if (!rd.equals(other.rd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return probability + " : " + rd;
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