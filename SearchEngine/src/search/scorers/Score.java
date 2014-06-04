package search.scorers;

import java.util.LinkedList;

public class Score {

	private LinkedList<Double> scores;

	public Score() {
		this.scores = new LinkedList<Double>();
	}
	
	public void add(double score) {
		this.scores.add(score);
	}
	
	public double getSum(){
		double total = 0;
		for (Double score: this.scores) {
			total += score;
		}
		return total;
	}

	@Override
	public String toString() {
		return "sum = "+getSum()+" "+scores.toString();
	}
	
}
