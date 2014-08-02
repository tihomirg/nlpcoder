package search.scorers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Score {

	private List<Double> scores;
	private List<Double> coefs;
	
	public Score(Double[] coefs) {
		this(Arrays.asList(coefs));
	}
	
	public Score(List<Double> coefs){
		this(new LinkedList<Double>(), coefs);
	}
	
	public Score(List<Double> scores, List<Double> coefs) {
		this.scores = scores;
		this.coefs = coefs;
	}

	public void add(double score) {
		this.scores.add(score);
	}
	
	public double getCoefSum(){
		double total = 0;
		for (int i=0; i< this.scores.size(); i++) {
			total += coefs.get(i) * scores.get(i);
		}
		return total;
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
		return "Score[ total = "+getSum()+", single = "+scores.toString()+"]";
	}
	
}
