package synthesis;

import java.util.LinkedList;
import java.util.List;

public class PartialExpressionScore implements Cloneable {
	private LinkedList<Double> compositionScores;
	private LinkedList<Double> declarationScores;
	private LinkedList<Double> individualSizeScores;
	private LinkedList<Double> mergeSizeScores;
	private LinkedList<Double> inputExprsScores;
	private LinkedList<Double> mergeScores;

	public PartialExpressionScore() {
		this.compositionScores = new LinkedList<Double>();
		this.declarationScores = new LinkedList<Double>();
		this.individualSizeScores = new LinkedList<Double>();
		this.inputExprsScores = new LinkedList<Double>();
		this.mergeSizeScores = new LinkedList<Double>();
		this.mergeScores = new LinkedList<Double>();
	}
	
	public void add(PartialExpressionScore score) {
		this.compositionScores.addAll(score.compositionScores);
		this.declarationScores.addAll(score.declarationScores);
		this.individualSizeScores.addAll(score.individualSizeScores);
		this.inputExprsScores.addAll(score.inputExprsScores);
		this.mergeSizeScores.addAll(score.mergeSizeScores);
		this.mergeScores.addAll(score.mergeScores);
	}

	public void addCompositionScore(double score){
		this.compositionScores.add(score);
	}
	
	public void addDeclarationScore(double score) {
		this.declarationScores.add(score);
	}
	
	public void addIndividualSizeScore(double score) {
		this.individualSizeScores.add(score);
	}
	
	public void addMergeSizeScore(double score) {
		this.mergeSizeScores.add(score);
	}
	
	public void addInputExprScore(double score) {
		this.inputExprsScores.add(score);
	}

	public void addMergeScore(double score) {
		this.mergeScores.add(score);
	}
	
	public double getValue(){
		return (sum(this.compositionScores) 
				+ sum(this.declarationScores) 
				+ sum(this.mergeScores) 
				+ sum(this.individualSizeScores) 
				+ sum(this.inputExprsScores) 
				+ sum(this.mergeSizeScores));
	}

	private double sum(List<Double> scores) {
		double sum = 0;
		for (double score: scores) {
			sum+=score;
		}
		return sum;
	}

	@Override
	protected PartialExpressionScore clone() {
		PartialExpressionScore clone = null;
		try {
			clone = (PartialExpressionScore) super.clone();
			clone.compositionScores = (LinkedList<Double>) this.compositionScores.clone();
			clone.declarationScores = (LinkedList<Double>) this.declarationScores.clone();
			clone.individualSizeScores = (LinkedList<Double>) this.individualSizeScores.clone();
			clone.inputExprsScores = (LinkedList<Double>) this.inputExprsScores.clone();
			clone.mergeSizeScores = (LinkedList<Double>) this.mergeSizeScores.clone();
			clone.mergeScores = (LinkedList<Double>) this.mergeScores.clone();
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public String toString() {
		return "Score [ value=" + getValue()
	            + ", compositionScores=" + compositionScores
				+ ", declarationScores=" + declarationScores
				+ ", individualSizeScores=" + individualSizeScores
				+ ", mergeSizeScores=" + mergeSizeScores
				+ ", inputExprsScores=" + inputExprsScores 
				+ ", mergeScore="+mergeScores+"]";
	}
}
