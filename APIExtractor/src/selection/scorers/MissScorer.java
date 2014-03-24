package selection.scorers;

public class MissScorer extends DecorateScorer {

	private int wordNum;
	private double penalty;
	private double lastScore;

	public MissScorer(Scorer score, double penalty, int wordNum) {
		super(score);
		
		this.wordNum = wordNum;
		this.penalty = penalty;
	}

	@Override
	public double getScore(double score) {
		return lastScore = score - ((this.wordNum - score) * penalty);
	}
	
	public String toString(int contextIndex){
		return Double.toString(lastScore);
	}
	
}
